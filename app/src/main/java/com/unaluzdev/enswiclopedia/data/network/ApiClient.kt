package com.unaluzdev.enswiclopedia.data.network

import com.unaluzdev.enswiclopedia.data.model.PeopleResponse
import com.unaluzdev.enswiclopedia.data.model.PlanetModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApiClient {
    @GET("people/")
    suspend fun getPeople(
        @Query("page") page: Int? = null,
        @Query("search") query: String? = null
    ): Response<PeopleResponse>
}

interface PlanetApiClient {
    @GET("planets/{id}/")
    suspend fun getPlanet(
        @Path("id") id: String
    ): Response<PlanetModel>
}