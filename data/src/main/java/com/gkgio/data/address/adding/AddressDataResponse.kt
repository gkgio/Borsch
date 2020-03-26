package com.gkgio.data.address.adding

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressDataResponse(
    @Json(name = "address")
    val address: AddressResponse
)