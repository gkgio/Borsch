package com.gkgio.data.basket

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BasketLunchResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "image_url")
    val imageUrl: String?,
    @Json(name = "meals")
    val meals: List<BasketMealResponse>
)