package com.unaluzdev.enswiclopedia.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.unaluzdev.enswiclopedia.databinding.ActivityMainBinding
import com.unaluzdev.enswiclopedia.ui.adapter.CharacterAdapter
import com.unaluzdev.enswiclopedia.util.VIEW_TYPE_ITEM
import com.unaluzdev.enswiclopedia.util.VIEW_TYPE_PROGRESS
import androidx.appcompat.widget.SearchView as CompatSearchView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val characterViewModel: CharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        // Observers
        characterViewModel.canLoadMore.observe(this) { canLoadMore ->
            val adapter = binding.recyclerViewCharacter.adapter as CharacterAdapter
            if (!canLoadMore) {
                adapter.removeLoadMoreView()
            } else {
                adapter.addLoadMoreView()
            }
        }

        characterViewModel.characterList.observe(this) {
            val adapter = binding.recyclerViewCharacter.adapter as CharacterAdapter
            adapter.addCharacters(it)
        }

        characterViewModel.searchResult.observe(this) { searchResult ->
            val adapter = binding.recyclerViewCharacter.adapter as CharacterAdapter
            if (searchResult.isNullOrEmpty())
                adapter.show(characterViewModel.characterList.value ?: ArrayList())
            else
                adapter.show(searchResult)
        }

        characterViewModel.uiState.observe(this) { state ->
            if (state.loading) binding.progressIndicator.show()
            else binding.progressIndicator.hide()

            if (!state.error.isNullOrBlank())
                Toast.makeText(this, state.error, Toast.LENGTH_LONG)
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
            val layoutManager = GridLayoutManager(this@MainActivity, 2)
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

}