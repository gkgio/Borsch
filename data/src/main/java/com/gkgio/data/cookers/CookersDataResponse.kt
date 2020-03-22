package com.gkgio.data.cookers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CookersDataResponse(
    @Json(name = "cookers")
    val cookers: List<CookerResponse>
)