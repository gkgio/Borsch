package com.gkgio.domain.address

data class GeoSuggestionsList(
    val lat: Double?,
    val lon: Double?,
    val query: String?,
    val suggestions: List<GeoSuggestion>
)