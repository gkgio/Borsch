package com.gkgio.domain.cookers

data class CookersWithoutAuthRequest(
    val lat: Double,
    val lon: Double,
    val distance: String? = null,
    val targets: List<String>? = null
)