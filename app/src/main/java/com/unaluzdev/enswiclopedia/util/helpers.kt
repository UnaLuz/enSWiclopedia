package com.unaluzdev.enswiclopedia.util

fun getId(url: String, category: String = "people"): String? {
    val pattern = """${BASE_INFO_URL}$category/(\d+)/""".toRegex()
    return pattern.find(url)?.groupValues?.get(1)
}

fun getImageUrl(url: String, category: String = "characters"): String =
    BASE_IMAGE_URL + "$category/${getId(url)}.jpg"