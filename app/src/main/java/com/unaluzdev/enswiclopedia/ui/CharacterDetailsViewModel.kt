package com.unaluzdev.enswiclopedia.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unaluzdev.enswiclopedia.data.model.SpeciesResponse
import com.unaluzdev.enswiclopedia.domain.GetPlanetUseCase
import com.unaluzdev.enswiclopedia.domain.GetSpeciesUseCase
import com.unaluzdev.enswiclopedia.domain.model.Planet
import com.unaluzdev.enswiclopedia.domain.model.Species
import kotlinx.coroutines.*

class CharacterDetailsViewModel : ViewModel() {

    companion object {
        const val TAG = "CharacterDetailsVM"
    }

    data class HomeworldState(
        val loading: Boolean = false,
        val error: String? = null,
        val planet: Planet? = null
    )

    data class SpeciesState(
        val loading: Boolean = false,
        val error: String? = null,
        var species: List<Species>? = null
    )

    private val _homeworldState = MutableLiveData(HomeworldState())
    val homeworldState: LiveData<HomeworldState> get() = _homeworldState

    private val _speciesState = MutableLiveData(SpeciesState())
    val speciesState: LiveData<SpeciesState> get() = _speciesState

    // UseCases
    val getPlanetUseCase = GetPlanetUseCase()
    val getSpeciesUseCase = GetSpeciesUseCase()

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

    fun onLoadSpecies(speciesIDs: List<String>) {
        viewModelScope.launch {
            _speciesState.value = SpeciesState(loading = true)
            val deferredSpecies = getDeferredSpeciesResponses(speciesIDs)
            try {
                val speciesResponseList = deferredSpecies.awaitAll()
                speciesResponseList.find { it.error != null }?.let {
                    _speciesState.value = SpeciesState(error = it.error)
                }
                updateSuccessSpeciesState(speciesResponseList)
            } catch (e: CancellationException) {
                Log.e(
                    TAG,
                    """An error occurred while fetching species: ${e.message}\n
                        ------\n
                        ${e.stackTraceToString()}""".trimMargin()
                )
                _speciesState.value = SpeciesState(loading = false, error = e.message)
            }
        }
    }

    private fun updateSuccessSpeciesState(speciesResponseList: List<SpeciesResponse>) {
        val speciesList = mapToSpecies(speciesResponseList)

        Log.d(TAG, "Species list size: ${speciesList.size}")
        _speciesState.value = _speciesState.value!!.copy(
            loading = false,
            species = speciesList
        )
    }

    private fun CoroutineScope.getDeferredSpeciesResponses(speciesIDs: List<String>) =
        speciesIDs.map { async { getSpeciesUseCase(it) } }

    private fun mapToSpecies(speciesResponses: List<SpeciesResponse>): List<Species> =
        speciesResponses.mapNotNull { it.species }
}