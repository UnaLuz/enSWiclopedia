package com.unaluzdev.enswiclopedia.ui

import androidx.lifecycle.ViewModel
import com.unaluzdev.enswiclopedia.data.CharacterModel
import com.unaluzdev.enswiclopedia.data.CharacterProvider
import com.unaluzdev.enswiclopedia.ui.adapter.CharacterAdapter

class CharacterViewModel : ViewModel() {

    var characterList: ArrayList<CharacterModel> = arrayListOf()
        private set

    fun onLoadCharacters(adapter: CharacterAdapter) {
        characterList = ArrayList(CharacterProvider.characters())
        val nNewItems = characterList.size
        adapter.characterList += characterList
        adapter.notifyItemRangeInserted(0, nNewItems)
    }
}