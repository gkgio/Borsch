package com.gkgio.data.address

data class GeoSuggestionsDataRequest(
    val lat: Double?,
    val lon: Double?,
    val query: String?
)