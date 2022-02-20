package com.unaluzdev.enswiclopedia.data

data class CharacterModel(
    val name: String,
    val birthYear: String,
    val eyeColor: String,
    val gender: String,
    val hairColor: String,
    val height: String,
    val mass: String,
    val skinColor: String,
    val homeWorld: String,
    val films: List<String>,
    val species: List<String>,
    val starship: List<String>,
    val vehicles: List<String>,
    val url: String,
    val created: String,
    val edited: String
)
