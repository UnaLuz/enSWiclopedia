package com.unaluzdev.enswiclopedia.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unaluzdev.enswiclopedia.domain.GetCharactersUseCase
import com.unaluzdev.enswiclopedia.domain.model.SWCharacter
import com.unaluzdev.enswiclopedia.util.plusAssign
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {

    data class UIState(val loading: Boolean = false, val error: String? = null)

    // Live Data
    var characterList = MutableLiveData<ArrayList<SWCharacter>>()
        private set

    private val _uiState = MutableLiveData(UIState())
    val uiState: LiveData<UIState> get() = _uiState

    // Use Cases
    var getCharactersUseCase = GetCharactersUseCase()

    fun onCreate() {
        viewModelScope.launch {
            _uiState.value = UIState(loading = true)
            val result = getCharactersUseCase()
            if (!result.isNullOrEmpty()) {
                characterList += ArrayList(result)
                _uiState.value = UIState(loading = false)
            } else {
                _uiState.value = UIState(
                    loading = false,
                    error = "Couldn't retrieve the data, check your network connection"
                )
            }
        }
    }
}