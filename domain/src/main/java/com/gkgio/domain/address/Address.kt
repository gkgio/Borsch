package com.gkgio.domain.address

import com.gkgio.domain.location.Coordinates

data class Address(
    val city: String,
    val country: String,
    val flat: String?,
    val house: String?,
    val location: Coordinates,
    val street: String
)