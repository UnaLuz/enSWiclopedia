package com.unaluzdev.enswiclopedia.util

fun getId(url: String): String? {
    val pattern = """${BASE_INFO_URL}people/(\d+)/""".toRegex()
    return pattern.find(url)?.groupValues?.get(1)
}

fun getCharacterImageUrl(url: String): String = BASE_IMAGE_URL + "characters/${getId(url)}.jpg"