package com.gkgio.domain.address

import com.gkgio.domain.location.Coordinates

data class Address(
    val id: String?,
    val city: String?,
    val country: String?,
    val flat: String?,
    val house: String?,
    val location: Coordinates,
    val street: String?,
    val block: String?
)