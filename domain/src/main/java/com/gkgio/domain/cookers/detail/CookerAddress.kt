package com.gkgio.domain.cookers.detail

import com.gkgio.domain.location.Coordinates

data class CookerAddress(
    val street: String,
    val house: String,
    val flat: String?,
    val floor: String?,
    val coordinates: Coordinates
)