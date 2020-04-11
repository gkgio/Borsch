package com.gkgio.data.basket

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BasketFoodIdRequest(
    @Json(name = "id")
    val id: String
)