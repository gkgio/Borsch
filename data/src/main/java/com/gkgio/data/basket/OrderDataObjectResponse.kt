package com.gkgio.data.basket

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class OrderDataObjectResponse(
    @Json(name = "order")
    val order: OrderDataResponse
)