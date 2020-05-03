package com.gkgio.data.basket

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BasketRepositoryData(
    @Json(name = "name")
    val name: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "count")
    var count: Int,
    @Json(name = "imageUrl")
    val imageUrl: String?,
    @Json(name = "price")
    val price: String,
    @Json(name = "priceOneItem")
    var priceOneItem: String,
    @Json(name = "type")
    val type: Int
)