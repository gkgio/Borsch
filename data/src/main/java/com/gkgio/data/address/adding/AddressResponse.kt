package com.gkgio.data.address.adding

data class AddressResponse(
    val city: String,
    val country: String,
    val flat: String?,
    val house: String?,
    val location: CoordinatesResponse,
    val street: String
)