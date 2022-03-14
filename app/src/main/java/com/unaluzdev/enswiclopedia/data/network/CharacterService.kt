package com.unaluzdev.enswiclopedia.data.network

import com.unaluzdev.enswiclopedia.core.RetrofitHelper
import com.unaluzdev.enswiclopedia.data.model.CharacterModel
import com.unaluzdev.enswiclopedia.data.model.PeopleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getPeople(page: Int): PeopleResponse? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(CharacterApiClient::class.java).getPeople(page = page)
            response.body()
        }
    }
}