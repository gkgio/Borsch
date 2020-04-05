package com.gkgio.data.cookers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LunchDataResponse(
    @Json(name = "lunch")
    val lunch: LunchResponse
)