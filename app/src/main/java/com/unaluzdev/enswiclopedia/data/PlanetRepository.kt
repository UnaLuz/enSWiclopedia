package com.unaluzdev.enswiclopedia.data

import com.unaluzdev.enswiclopedia.data.network.PlanetService
import com.unaluzdev.enswiclopedia.domain.model.toDomain

class PlanetRepository {
    private val api = PlanetService()

    suspend fun getPlanet(id: String) = api.getPlanet(id)?.toDomain()
}