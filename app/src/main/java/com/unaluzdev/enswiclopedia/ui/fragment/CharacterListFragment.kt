package com.unaluzdev.enswiclopedia.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.unaluzdev.enswiclopedia.databinding.FragmentCharacterListBinding
import com.unaluzdev.enswiclopedia.ui.CharacterViewModel
import com.unaluzdev.enswiclopedia.ui.adapter.CharacterAdapter
import com.unaluzdev.enswiclopedia.util.VIEW_TYPE_ITEM
import com.unaluzdev.enswiclopedia.util.VIEW_TYPE_PROGRESS
import androidx.appcompat.widget.SearchView as CompatSearchView

/**
 * A simple [Fragment] subclass.
 * Use the [CharacterListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CharacterListFragment : Fragment() {
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!
    private val characterViewModel: CharacterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        Log.d(TAG, "onCreateView called")
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()

        // Observers
        characterViewModel.canLoadMore.observe(viewLifecycleOwner) { canLoadMore ->
            val adapter = binding.recyclerViewCharacter.adapter as CharacterAdapter
            if (!canLoadMore) {
                adapter.removeLoadMoreView()
            } else {
                adapter.addLoadMoreView()
            }
        }

        characterViewModel.characterList.observe(viewLifecycleOwner) {
            val adapter = binding.recyclerViewCharacter.adapter as CharacterAdapter
            adapter.addCharacters(it)
        }

        characterViewModel.searchResult.observe(viewLifecycleOwner) { searchResult ->
            val adapter = binding.recyclerViewCharacter.adapter as CharacterAdapter
            if (searchResult.isNullOrEmpty())
                adapter.show(characterViewModel.characterList.value ?: ArrayList())
            else
                adapter.show(searchResult)
        }

        characterViewModel.uiState.observe(viewLifecycleOwner) { state ->
            if (state.loading) binding.progressIndicator.show()
            else binding.progressIndicator.hide()

            if (!state.error.isNullOrBlank())
                Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG)
                    .show()
        }

        // Finish onCreate
        characterViewModel.onCreate()

        binding.searchView.setOnQueryTextListener(object : CompatSearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrBlank()) {
                    characterViewModel.onCleanSearch()
                }
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank())
                    characterViewModel.onSearchRequest(query)
                return false
            }
        })
    }

    private fun initRecyclerView() {
        with(binding.recyclerViewCharacter) {
            // Create layout manager and adapter
            val layoutManager = GridLayoutManager(requireContext(), 2)
            val adapter = CharacterAdapter(ArrayList()) { loadMore() }
            // Initialize them in the recycler view
            this.layoutManager = layoutManager
            this.adapter = adapter
            addCustomSpanSizeLookup(layoutManager, adapter)
        }
    }

    private fun addCustomSpanSizeLookup(
        layoutManager: GridLayoutManager,
        adapter: CharacterAdapter
    ) {
        layoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (adapter.getItemViewType(position)) { // A sealed class or enum would be better
                        VIEW_TYPE_ITEM -> 1
                        VIEW_TYPE_PROGRESS -> layoutManager.spanCount
                        else -> -1
                    }
                }
            }
    }

    private fun loadMore() {
        if (characterViewModel.searchResult.value.isNullOrEmpty())
            characterViewModel.onLoadMore()
    }

    companion object {
        const val TAG = "CharacterListFragment"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CharacterListFragment.
         */
        @JvmStatic
        fun newInstance() = CharacterListFragment()
    }
}