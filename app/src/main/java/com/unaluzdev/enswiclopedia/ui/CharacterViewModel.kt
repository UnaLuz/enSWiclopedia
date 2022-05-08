package com.unaluzdev.enswiclopedia.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unaluzdev.enswiclopedia.domain.GetCharacterSearchResultsUseCase
import com.unaluzdev.enswiclopedia.domain.GetCharactersUseCase
import com.unaluzdev.enswiclopedia.domain.model.SWCharacter
import com.unaluzdev.enswiclopedia.util.plusAssign
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {

    data class UIState(
        val loading: Boolean = false,
        val error: String? = null
    )

    // Live Data
    var canLoadMore = MutableLiveData(true)
        private set

    var characterList = MutableLiveData<ArrayList<SWCharacter>>()
        private set
    var searchResult = MutableLiveData<ArrayList<SWCharacter>?>()
        private set

    private val _uiState = MutableLiveData(UIState())
    val uiState: LiveData<UIState> get() = _uiState

    // Use Cases
    var getCharactersUseCase = GetCharactersUseCase()
    var getCharacterSearchResultsUseCase = GetCharacterSearchResultsUseCase()

    fun onCreate() {
        if (characterList.value.isNullOrEmpty())
            onLoadMore()
        if(!searchResult.value.isNullOrEmpty())
            canLoadMore.postValue(false)
    }

    fun onLoadMore() {
        viewModelScope.launch {
            _uiState.value = UIState(loading = true)
            val result = getCharactersUseCase()
            if (result != null) {
                characterList += ArrayList(result.people)
                _uiState.value = UIState(loading = false)
                if (!result.hasNextPage) canLoadMore.postValue(false)
            } else {
                _uiState.value = UIState(
                    loading = false,
                    error = "Couldn't retrieve the data, check your network connection"
                )
            }
        }
    }

    fun onSearchRequest(query: String) {
        viewModelScope.launch {
            _uiState.value = UIState(loading = true)
            val result = getCharacterSearchResultsUseCase(query = query)
            if (result != null) {
                searchResult.value = ArrayList(result.people)
                _uiState.value = UIState(loading = false)
                canLoadMore.postValue(false)
            } else {
                _uiState.value = UIState(
                    loading = false,
                    error = "Couldn't retrieve the data, check your network connection"
                )
            }
        }
    }

    fun onCleanSearch() {
        searchResult.value = null
        canLoadMore.postValue(true)
    }

    fun getCharacter(condition: (SWCharacter) -> Boolean): SWCharacter? {
        return characterList.value?.find(condition)
            ?: searchResult.value?.find(condition)
    }
}