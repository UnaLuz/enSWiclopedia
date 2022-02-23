package com.unaluzdev.enswiclopedia.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unaluzdev.enswiclopedia.data.model.CharacterModel
import com.unaluzdev.enswiclopedia.domain.GetCharactersUseCase
import com.unaluzdev.enswiclopedia.ui.adapter.CharacterAdapter
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {

    var characterList: ArrayList<CharacterModel> = arrayListOf()
        private set

    var getCharactersUseCase = GetCharactersUseCase()

    fun onCreate(adapter: CharacterAdapter) {
        viewModelScope.launch {
            val result = getCharactersUseCase()
            if (!result.isNullOrEmpty()) {
                characterList = ArrayList(result)
                adapter.addCharacters(characterList)
            }
        }
    }
}