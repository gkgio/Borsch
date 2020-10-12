package com.gkgio.domain.address

import com.gkgio.domain.location.Coordinates

data class AddressAddingRequest(
    val city: String?,
    val country: String?,
    val flat: String?,
    val house: String?,
    val location: Coordinates,
    val street: String?,
    val block: String?,
    val cityArea: String?,
    val region: String?,
    val cityDistrict: String?
)