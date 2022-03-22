package com.unaluzdev.enswiclopedia.domain

import com.unaluzdev.enswiclopedia.data.CharacterRepository
import com.unaluzdev.enswiclopedia.domain.model.SWPeopleResponse

class GetCharacterSearchResultsUseCase {

    private val repository = CharacterRepository()

    suspend operator fun invoke(query: String): SWPeopleResponse? =
        repository.getCharacterSearchResults(query = query)

}