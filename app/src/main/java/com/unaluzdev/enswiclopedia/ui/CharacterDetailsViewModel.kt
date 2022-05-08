package com.unaluzdev.enswiclopedia.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unaluzdev.enswiclopedia.domain.GetPlanetUseCase
import com.unaluzdev.enswiclopedia.domain.model.Planet
import kotlinx.coroutines.launch

class CharacterDetailsViewModel : ViewModel() {

    data class HomeworldState(
        val loading: Boolean = false,
        val error: String? = null,
        val planet: Planet? = null
    )

    private val _homeworldState = MutableLiveData(HomeworldState())
    val homeworldState: LiveData<HomeworldState> get() = _homeworldState

    // UseCases
    val getPlanetUseCase = GetPlanetUseCase()

    fun onLoadHomeworld(homeworldID: String) {
        viewModelScope.launch {
            _homeworldState.value = HomeworldState(
                loading = true
            )
            val response = getPlanetUseCase(homeworldID)
            if (response != null) {
                _homeworldState.value = HomeworldState(
                    loading = false,
                    planet = response
                )
            } else {
                _homeworldState.value = HomeworldState(
                    loading = false,
                    error = "Couldn't retrieve the data, check your network connection"
                )
            }
        }
    }
}