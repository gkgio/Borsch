package com.gkgio.data.address

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeoSuggestionsDataRequest(
    @Json(name = "lat")
    val lat: Double?,
    @Json(name = "lon")
    val lon: Double?,
    @Json(name = "query")
    val query: String?,
    @Json(name = "count")
    val count: Int
)