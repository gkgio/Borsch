package com.gkgio.domain.cookers

data class CookersRequest(
    val addressId: String? = null,
    val distance: String? = null,
    val targets: List<String>? = null
)