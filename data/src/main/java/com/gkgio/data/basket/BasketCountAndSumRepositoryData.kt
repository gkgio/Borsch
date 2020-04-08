package com.gkgio.data.basket

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BasketCountAndSumRepositoryData(
    @Json(name = "count")
    val count: Int,
    @Json(name = "sum")
    val sum: String,
    @Json(name = "cookerId")
    val cookerId: String
)