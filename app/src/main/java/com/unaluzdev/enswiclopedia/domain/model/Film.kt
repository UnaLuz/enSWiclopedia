package com.unaluzdev.enswiclopedia.domain.model

import com.unaluzdev.enswiclopedia.data.model.FilmModel
import com.unaluzdev.enswiclopedia.util.Category
import com.unaluzdev.enswiclopedia.util.getImageUrl

data class Film(
    val characters: List<String>,
    val director: String,
    val episode_id: Int,
    val opening_crawl: String,
    val producer: String,
    val release_date: String,
    val title: String,
    val imgUrl: String,
)

fun FilmModel.toDomain() = Film(
    characters = characters,
    director = director,
    episode_id = episode_id,
    opening_crawl = opening_crawl,
    producer = producer,
    release_date = release_date,
    title = title,
    imgUrl = getImageUrl(this.url, Category.FILMS)
)