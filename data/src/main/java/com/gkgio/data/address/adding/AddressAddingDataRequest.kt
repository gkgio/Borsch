package com.gkgio.data.address.adding

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressAddingDataRequest(
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
    val block: String?,
    @Json(name = "area")
    val cityArea: String?,
    @Json(name = "region")
    val region: String?,
    @Json(name = "city_district")
    val cityDistrict: String?,
    @Json(name = "main")
    val main: Boolean = true
)