package com.gkgio.data.address.adding

data class AddressAddingDataRequest(
    val city: String,
    val country: String,
    val flat: String?,
    val house: String?,
    val location: CoordinatesRequest,
    val street: String
)