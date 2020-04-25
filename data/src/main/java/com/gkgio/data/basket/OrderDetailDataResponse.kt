package com.gkgio.data.basket

import com.gkgio.data.cookers.CookerResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderDetailDataResponse(
    @Json(name = "cooker")
    val cooker: CookerResponse,
    @Json(name = "order")
    val order: OrderDetailResponse
)