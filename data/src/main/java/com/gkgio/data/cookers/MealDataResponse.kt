package com.gkgio.data.cookers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MealDataResponse(
    @Json(name = "meal")
    val meal: MealResponse
)