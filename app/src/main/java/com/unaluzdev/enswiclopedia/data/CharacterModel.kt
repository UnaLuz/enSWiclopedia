package com.unaluzdev.enswiclopedia.data

data class CharacterModel(
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
    val url: String,
    val created: String,
    val edited: String
)
