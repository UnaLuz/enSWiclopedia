package com.unaluzdev.enswiclopedia.domain

import com.unaluzdev.enswiclopedia.data.model.CharacterModel
import com.unaluzdev.enswiclopedia.data.CharacterRepository

class GetCharactersUseCase {

    private val repository = CharacterRepository()

    suspend operator fun invoke(): List<CharacterModel>? = repository.getCharacters()
}