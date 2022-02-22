package com.unaluzdev.enswiclopedia.data.network

import com.unaluzdev.enswiclopedia.data.model.PeopleResponse
import retrofit2.Response
import retrofit2.http.GET

interface CharacterApiClient {
    @GET("people/")
    suspend fun getPeople() : Response<PeopleResponse>
}