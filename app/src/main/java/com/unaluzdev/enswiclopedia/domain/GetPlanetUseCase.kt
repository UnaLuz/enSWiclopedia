package com.unaluzdev.enswiclopedia.domain

import com.unaluzdev.enswiclopedia.data.PlanetRepository
import com.unaluzdev.enswiclopedia.domain.model.Planet

class GetPlanetUseCase {
    private val repository = PlanetRepository()

        suspend operator fun invoke(id: String): Planet? = repository.getPlanet(id)
}