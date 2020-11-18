package com.gkgio.data.basket

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BasketOrderDataRequest(
    @Json(name = "meal_ids")
    val mealsIds: List<String>?,
    @Json(name = "lunche_ids")
    val lunchesIds: List<String>?,
    @Json(name = "cooker_id")
    val cookerId: String,
    @Json(name = "type")
    val type: String = "pickup"
)