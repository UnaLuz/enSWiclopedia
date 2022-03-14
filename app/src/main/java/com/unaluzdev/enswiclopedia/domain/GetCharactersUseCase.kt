package com.unaluzdev.enswiclopedia.domain

import com.unaluzdev.enswiclopedia.data.CharacterRepository
import com.unaluzdev.enswiclopedia.domain.model.SWPeopleResponse

class GetCharactersUseCase {

    private val repository = CharacterRepository()

    suspend operator fun invoke(): SWPeopleResponse? = repository.getCharacters()
}