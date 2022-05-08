package com.unaluzdev.enswiclopedia.util

enum class Category(val alias: String? = null) {
    PEOPLE("characters"), PLANETS, SPECIES, FILMS
}

fun getId(url: String, category: Category = Category.PEOPLE): String? {
    val pattern = """${BASE_INFO_URL}$category/(\d+)/""".toRegex()
    return pattern.find(url)?.groupValues?.get(1)
}

fun getImageUrl(url: String, category: Category = Category.PEOPLE): String =
    BASE_IMAGE_URL + "${category.alias ?: category}/${getId(url, category)}.jpg"