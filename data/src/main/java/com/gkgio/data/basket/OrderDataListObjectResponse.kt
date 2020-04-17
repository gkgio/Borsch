package com.gkgio.data.basket

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class OrderDataListObjectResponse(
    @Json(name = "orders")
    val orders: List<OrderDataResponse>
)