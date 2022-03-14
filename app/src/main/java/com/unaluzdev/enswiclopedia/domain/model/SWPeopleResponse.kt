package com.unaluzdev.enswiclopedia.domain.model

import com.unaluzdev.enswiclopedia.data.model.PeopleResponse

data class SWPeopleResponse(
    val hasNextPage: Boolean,
    val people: List<SWCharacter>
)

fun PeopleResponse.toDomain() = SWPeopleResponse(
    hasNextPage = !this.next.isNullOrBlank(),
    people = this.people.map { it.toDomain() }
)