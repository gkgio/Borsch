package com.gkgio.data.basket

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BasketOrderDataRequest(
    @Json(name = "meals")
    val mealsIds: List<BasketFoodIdRequest>?,
    @Json(name = "lunches")
    val lunchesIds: List<BasketFoodIdRequest>?,
    @Json(name = "type")
    val type: String
)