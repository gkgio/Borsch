package com.gkgio.data.cookers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CookersDataRequest(
    @Json(name = "address_id")
    val addressId: String?,
    @Json(name = "distance")
    val distance: String?,
    @Json(name = "targets")
    val targets: List<String>?
)