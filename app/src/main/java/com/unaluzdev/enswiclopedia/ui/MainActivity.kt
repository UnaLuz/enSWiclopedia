package com.unaluzdev.enswiclopedia.ui

import android.os.Bundle
import android.widget.Toast
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
        characterViewModel.onCreate()

        // Observers
        characterViewModel.characterList.observe(this) {
            val adapter = binding.recyclerViewCharacter.adapter as CharacterAdapter
            adapter.addCharacters(it)
        }
        characterViewModel.error.observe(this) { errorMsg ->
            if (errorMsg.isNotBlank())
                Toast.makeText(this, errorMsg, Toast.LENGTH_LONG)
                    .show()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerViewCharacter.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewCharacter.adapter =
            CharacterAdapter(ArrayList())
    }

}