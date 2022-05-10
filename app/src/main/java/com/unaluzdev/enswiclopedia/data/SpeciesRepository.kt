package com.unaluzdev.enswiclopedia.data

import android.util.Log
import com.unaluzdev.enswiclopedia.data.model.SpeciesResponse
import com.unaluzdev.enswiclopedia.data.network.SpeciesService
import com.unaluzdev.enswiclopedia.domain.model.toDomain

class SpeciesRepository {
    private val api = SpeciesService()

    suspend fun getSpecies(id: String) = with(api.getSpecies(id)) {
        try {
            if (isSuccessful) {
                SpeciesResponse(species = body()?.toDomain())
            } else {
                SpeciesResponse(error = errorBody().toString())
            }
        } catch (e: Exception) {
            Log.e("SpeciesRepository", "Error when retrieving species: ${e.stackTraceToString()}")
            SpeciesResponse(error = e.message)
        }
    }

}