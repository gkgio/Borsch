package com.gkgio.data.address

class GeoSuggestionsListResponse(
    val lat: Double?,
    val lon: Double?,
    val query: String?,
    val suggestions: List<GeoSuggestionResponse>
)