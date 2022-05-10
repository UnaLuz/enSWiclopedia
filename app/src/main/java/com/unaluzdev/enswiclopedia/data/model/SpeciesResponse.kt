package com.unaluzdev.enswiclopedia.data.model

import com.unaluzdev.enswiclopedia.domain.model.Species

data class SpeciesResponse (
    val error: String? = null,
    val species: Species? = null
)