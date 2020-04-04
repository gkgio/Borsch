package com.gkgio.data.cookers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CookersDataWithoutAuthRequest(
    @Json(name = "lat")
    val lat: Int,
    @Json(name = "lon")
    val lon: Int,
    @Json(name = "distance")
    val distance: String?,
    @Json(name = "targets")
    val targets: List<String>?
)