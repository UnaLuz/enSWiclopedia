package com.unaluzdev.enswiclopedia.domain.model

import com.unaluzdev.enswiclopedia.data.model.PlanetModel
import com.unaluzdev.enswiclopedia.util.Category
import com.unaluzdev.enswiclopedia.util.getId
import com.unaluzdev.enswiclopedia.util.getImageUrl

data class Planet(
    val climate: String,
    val diameter: String,
    val films: List<String>,
    val gravity: String,
    val name: String,
    val orbital_period: String,
    val population: String,
    val rotation_period: String,
    val surface_water: String,
    val terrain: String,
    val id: Int,
    val imgUrl: String
)

fun PlanetModel.toDomain() = Planet(
    climate = climate,
    diameter = diameter,
    films = films,
    gravity = gravity,
    name = name,
    orbital_period = orbital_period,
    population = population,
    rotation_period = rotation_period,
    surface_water = surface_water,
    terrain = terrain,
    id = getId(this.url, Category.PLANETS)?.toInt() ?: 0,
    imgUrl = getImageUrl(this.url, Category.PLANETS)
)