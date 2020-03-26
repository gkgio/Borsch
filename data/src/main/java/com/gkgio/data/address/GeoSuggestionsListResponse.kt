package com.gkgio.data.address

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class GeoSuggestionsListResponse(
    @Json(name = "lat")
    val lat: Double?,
    @Json(name = "lon")
    val lon: Double?,
    @Json(name = "query")
    val query: String?,
    @Json(name = "suggestions")
    val suggestions: List<GeoSuggestionResponse>
)