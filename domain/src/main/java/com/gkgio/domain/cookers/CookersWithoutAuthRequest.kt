package com.gkgio.domain.cookers

data class CookersWithoutAuthRequest(
    val lat: Int,
    val lon: Int,
    val distance: String? = null,
    val targets: List<String>? = null
)