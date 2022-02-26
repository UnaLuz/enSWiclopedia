package com.unaluzdev.enswiclopedia.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unaluzdev.enswiclopedia.data.model.CharacterModel
import com.unaluzdev.enswiclopedia.domain.GetCharactersUseCase
import com.unaluzdev.enswiclopedia.util.plusAssign
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {

    // Live Data
    var characterList = MutableLiveData<ArrayList<CharacterModel>>()
        private set

    var error = MutableLiveData<String>()
        private set

    // Use Cases
    var getCharactersUseCase = GetCharactersUseCase()

    fun onCreate() {
        viewModelScope.launch {
            val result = getCharactersUseCase()
            if (!result.isNullOrEmpty()) {
                characterList += ArrayList(result)
                error.postValue("")
            } else {
                error.postValue("Couldn't retrieve the data, check your network connection")
            }
        }
    }
}