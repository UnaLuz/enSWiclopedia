package com.unaluzdev.enswiclopedia.data

import com.unaluzdev.enswiclopedia.data.network.CharacterService
import com.unaluzdev.enswiclopedia.domain.model.SWPeopleResponse
import com.unaluzdev.enswiclopedia.domain.model.toDomain

class CharacterRepository {

    private val api = CharacterService()

    private var currentPage = 0
    private var hasNextPage = true

    suspend fun getCharacters(): SWPeopleResponse? {
        if (!hasNextPage) return null
        val response = api.getPeople(page = currentPage + 1)?.toDomain()
        if (response != null) {
            currentPage += 1
            hasNextPage = response.hasNextPage
        }
        return response
    }
}