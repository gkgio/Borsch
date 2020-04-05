package com.gkgio.data.cookers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LunchResponse(
    @Json(name = "available")
    val available: Boolean,
    @Json(name = "available_time_from")
    val availableTimeFrom: String?,
    @Json(name = "available_time_to")
    val availableTimeTo: String?,
    @Json(name = "discount_percent")
    val discountPercent: Int,
    @Json(name = "id")
    val id: String,
    @Json(name = "image_url")
    val imageUrl: String?,
    @Json(name = "meals")
    val meals: List<MealResponse>,
    @Json(name = "name")
    val name: String,
    @Json(name = "price")
    val price: String,
    @Json(name = "calories")
    val calories: String,
    @Json(name = "weight")
    val weight: Int
)