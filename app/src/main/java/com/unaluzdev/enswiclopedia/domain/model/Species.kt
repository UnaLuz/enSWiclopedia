package com.unaluzdev.enswiclopedia.domain.model

import com.unaluzdev.enswiclopedia.data.model.SpeciesModel
import com.unaluzdev.enswiclopedia.util.Category
import com.unaluzdev.enswiclopedia.util.getId
import com.unaluzdev.enswiclopedia.util.getImageUrl

data class Species(
    val averageHeight: String,
    val averageLifespan: String,
    val classification: String,
    val designation: String,
    val films: List<String>,
    val homeworld: String,
    val language: String,
    val name: String,
    val id: Int,
    val imgUrl: String
)

fun SpeciesModel.toDomain() = Species(
    averageHeight = averageHeight,
    averageLifespan = averageLifespan,
    classification = classification,
    designation = designation,
    films = films,
    homeworld = homeworld,
    language = language,
    name = name,
    id = getId(url, Category.SPECIES)?.toInt() ?: 0,
    imgUrl = getImageUrl(this.url, Category.SPECIES),
)