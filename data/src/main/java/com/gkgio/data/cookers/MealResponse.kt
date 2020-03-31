package com.gkgio.data.cookers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MealResponse(
    @Json(name = "available")
    val available: Boolean?,
    @Json(name = "calories")
    val calories: Int,
    @Json(name = "cook_time")
    val cookTime: Int,
    @Json(name = "description")
    val description: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "image_url")
    val imageUrl: String,
    @Json(name = "ingredients")
    val ingredients: List<String>,
    @Json(name = "name")
    val name: String,//Meal name
    @Json(name = "portions")
    val portions: Int,//Amount of available portions
    @Json(name = "price")
    val price: String,
    @Json(name = "tags")
    val tags: List<String>,
    @Json(name = "type")
    val type: String?,
    @Json(name = "weight")
    val weight: Int
)