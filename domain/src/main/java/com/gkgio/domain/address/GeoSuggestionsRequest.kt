package com.gkgio.domain.address

data class GeoSuggestionsRequest(
    val lat: Double? = null,
    val lon: Double? = null,
    val query: String? = null,
    val count: Int = 1
)