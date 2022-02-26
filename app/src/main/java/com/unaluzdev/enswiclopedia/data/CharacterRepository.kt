package com.unaluzdev.enswiclopedia.data

import com.unaluzdev.enswiclopedia.data.network.CharacterService
import com.unaluzdev.enswiclopedia.domain.model.SWCharacter
import com.unaluzdev.enswiclopedia.domain.model.toDomain

class CharacterRepository {

    private val api = CharacterService()

    suspend fun getCharacters(): List<SWCharacter>? {
        return api.getPeople()?.map { it.toDomain() }
    }
}