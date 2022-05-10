package com.unaluzdev.enswiclopedia.domain

import com.unaluzdev.enswiclopedia.data.SpeciesRepository
import com.unaluzdev.enswiclopedia.data.model.SpeciesResponse

class GetSpeciesUseCase {
    private val repository = SpeciesRepository()

    suspend operator fun invoke(id: String): SpeciesResponse {
        return repository.getSpecies(id)
    }
}