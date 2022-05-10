package com.unaluzdev.enswiclopedia.data.network

import com.unaluzdev.enswiclopedia.core.RetrofitHelper
import com.unaluzdev.enswiclopedia.data.model.SpeciesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class SpeciesService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getSpecies(id: String): Response<SpeciesModel> {
        return withContext(Dispatchers.IO) {
            retrofit.create(SpeciesApiClient::class.java).getSpecies(id)
        }
    }
}