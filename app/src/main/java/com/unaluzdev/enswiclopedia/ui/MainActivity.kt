package com.unaluzdev.enswiclopedia.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.unaluzdev.enswiclopedia.databinding.ActivityMainBinding
import com.unaluzdev.enswiclopedia.ui.adapter.CharacterAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val characterViewModel: CharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        loadCharacters()
    }

    private fun initRecyclerView() {
        binding.recyclerViewCharacter.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewCharacter.adapter =
            CharacterAdapter(characterViewModel.characterList)
    }

    private fun loadCharacters() {
        val adapter = binding.recyclerViewCharacter.adapter as CharacterAdapter
        characterViewModel.onLoadCharacters(adapter)
    }
}