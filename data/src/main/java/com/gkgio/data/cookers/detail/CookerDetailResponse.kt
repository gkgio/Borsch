package com.gkgio.data.cookers.detail

import com.gkgio.data.cookers.LunchResponse
import com.gkgio.data.cookers.MealResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CookerDetailResponse(
    @Json(name = "banned")
    val banned: Boolean?,
    @Json(name = "certified")
    val certified: Boolean,
    @Json(name = "commission")
    val commission: String?,
    @Json(name = "id")
    val id: String,
    @Json(name = "meals")
    val meals: List<MealResponse>,
    @Json(name = "lunches")
    val lunches: List<LunchResponse>?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "paid")
    val paid: Boolean?,
    @Json(name = "phone")
    val phone: String?,
    @Json(name = "rating")
    val rating: Double?,
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
    val onDuty: Boolean,//Whether user is online and accepting orders
    @Json(name = "address")
    val address: CookerAddressResponse?
)