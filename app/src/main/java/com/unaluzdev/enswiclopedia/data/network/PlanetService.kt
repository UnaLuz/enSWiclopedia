package com.unaluzdev.enswiclopedia.data.network

import com.unaluzdev.enswiclopedia.core.RetrofitHelper
import com.unaluzdev.enswiclopedia.data.model.PlanetModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlanetService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getPlanet(id: String): PlanetModel? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(PlanetApiClient::class.java).getPlanet(id)
            response.body()
        }
    }
}