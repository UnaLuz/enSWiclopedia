package com.unaluzdev.enswiclopedia.data

import com.unaluzdev.enswiclopedia.data.model.CharacterModel
import com.unaluzdev.enswiclopedia.data.model.CharacterProvider
import com.unaluzdev.enswiclopedia.data.network.CharacterService

class CharacterRepository {

    private val api = CharacterService()

    suspend fun getCharacters(): List<CharacterModel>? {
        val response: List<CharacterModel>? = api.getPeople()
        CharacterProvider.characterList += response ?: emptyList()
        return response
    }
}