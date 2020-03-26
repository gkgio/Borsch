package com.gkgio.data.address.adding

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoordinatesRequest(
    @Json(name = "lat")
    val lat: Double,
    @Json(name = "lon")
    val lon: Double
)