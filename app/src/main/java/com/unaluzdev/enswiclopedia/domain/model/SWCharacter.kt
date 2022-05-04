package com.unaluzdev.enswiclopedia.domain.model

import com.unaluzdev.enswiclopedia.data.model.CharacterModel
import com.unaluzdev.enswiclopedia.util.getImageUrl

data class SWCharacter(
    val imgUrl: String,
    val name: String,
    val birth_year: String,
    val eye_color: String,
    val gender: String,
    val hair_color: String,
    val height: String,
    val mass: String,
    val skin_color: String,
    val homeworld: String,
    val films: List<String>,
    val species: List<String>,
    val starships: List<String>,
    val vehicles: List<String>,
)

fun CharacterModel.toDomain() = SWCharacter(
    imgUrl = getImageUrl(this.url),
    name,
    birth_year,
    eye_color,
    gender,
    hair_color,
    height,
    mass,
    skin_color,
    homeworld,
    films,
    species,
    starships,
    vehicles
)
