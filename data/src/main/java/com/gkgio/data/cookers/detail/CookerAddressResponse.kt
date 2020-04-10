package com.gkgio.data.cookers.detail

import com.gkgio.data.address.adding.CoordinatesResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CookerAddressResponse(
    @Json(name = "street")
    val street: String,
    @Json(name = "house")
    val house: String,
    @Json(name = "flat")
    val flat: String?,
    @Json(name = "floor")
    val floor: String?,
    @Json(name = "location")
    val coordinates: CoordinatesResponse
)