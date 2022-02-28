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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val characterViewModel: CharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        // Observers
        characterViewModel.characterList.observe(this) {
            val adapter = binding.recyclerViewCharacter.adapter as CharacterAdapter
            adapter.addCharacters(it)
            adapter.moveLoadView(adapter.itemCount - 1) { loadMore() }
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
    }

    private fun initRecyclerView() {
        with(binding.recyclerViewCharacter) {
            // Create layout manager and adapter
            val layoutManager = GridLayoutManager(this@MainActivity, 2)
            val adapter = CharacterAdapter(ArrayList())
            // Initialize them in the recycler view
            this.layoutManager = layoutManager
            this.adapter = adapter
            addCustomSpanSizeLookup(layoutManager, adapter)
            adapter.addLoadingView { loadMore() }
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
        Toast.makeText(this@MainActivity, "Loading more", Toast.LENGTH_SHORT).show()
    }

}