package com.gkgio.data.basket

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderCancelResponse(
    @Json(name = "ok")
    val ok: Boolean
)