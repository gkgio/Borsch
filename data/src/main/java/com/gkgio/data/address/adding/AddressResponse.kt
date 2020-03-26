package com.gkgio.data.address.adding

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressResponse(
    @Json(name = "city")
    val city: String,
    @Json(name = "country")
    val country: String,
    @Json(name = "flat")
    val flat: String?,
    @Json(name = "house")
    val house: String?,
    @Json(name = "location")
    val location: CoordinatesResponse,
    @Json(name = "street")
    val street: String
)