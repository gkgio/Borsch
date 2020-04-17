package com.gkgio.data.address.repository

import com.gkgio.data.address.adding.CoordinatesRequest
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressRepositoryRequest(
    @Json(name = "city")
    val city: String?,
    @Json(name = "country")
    val country: String?,
    @Json(name = "flat")
    val flat: String?,
    @Json(name = "house")
    val house: String?,
    @Json(name = "location")
    val location: CoordinatesRequest,
    @Json(name = "street")
    val street: String?,
    @Json(name = "block")
    val block: String?
) {
    @Json(name = "id")
    var id: String? = null
}