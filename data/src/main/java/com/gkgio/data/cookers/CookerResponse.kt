package com.gkgio.data.cookers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CookerResponse(
    @Json(name = "banned")
    val banned: Boolean,
    @Json(name = "certified")
    val certified: Boolean,
    @Json(name = "commission")
    val commission: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "meals")
    val meals: List<MealResponse>,
    @Json(name = "name")
    val name: String,
    @Json(name = "paid")
    val paid: String,
    @Json(name = "phone")
    val phone: String,
    @Json(name = "rating")
    val rating: String,
    @Json(name = "suspended")
    val suspended: Boolean,
    @Json(name = "verified")
    val verified: Boolean
)