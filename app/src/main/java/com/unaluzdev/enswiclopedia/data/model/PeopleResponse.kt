package com.unaluzdev.enswiclopedia.data.model

import com.google.gson.annotations.SerializedName

data class PeopleResponse (
    val count: Int,
    val next: String?,
    val previous: String?,
    @SerializedName("results") val people: List<CharacterModel>
)