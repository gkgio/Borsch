package com.gkgio.data.cookers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CookerResponse(
    @Json(name = "banned")
    val banned: Boolean?,
    @Json(name = "certified")
    val certified: Boolean,
    @Json(name = "id")
    val id: String,
    @Json(name = "meals")
    val meals: List<MealResponse>,
    @Json(name = "lunches")
    val lunches: List<LunchResponse>?,
    @Json(name = "name")
    val name: String,
    @Json(name = "phone")
    val phone: String?,
    @Json(name = "rating")
    val rating: String,
    @Json(name = "suspended")
    val suspended: Boolean?,
    @Json(name = "verified")
    val verified: Boolean?,
    @Json(name = "avatar_url")
    val avatarUrl: String?,
    @Json(name = "delivery")
    val delivery: Boolean,
    @Json(name = "country_tags")
    val countryTags: List<String>?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "on_duty")
    val onDuty: Boolean //Whether user is online and accepting orders
)