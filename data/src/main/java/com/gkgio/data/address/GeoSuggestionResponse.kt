package com.gkgio.data.address

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeoSuggestionResponse(
    @Json(name = "data")
    val data: GeoSuggestionDataResponse,
    @Json(name = "value")
    val value: String //то что показывается клиенту
)