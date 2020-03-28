package com.gkgio.data.address

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeoSuggestionDataResponse(
    @Json(name = "city")
    val city: String?,
    @Json(name = "city_area")
    val cityArea: String?,
    @Json(name = "city_district")
    val cityDistrict: String?,
    @Json(name = "city_type_full")
    val cityTypeFull: String?,
    @Json(name = "country")
    val country: String?,
    @Json(name = "flat")
    val flat: String?,
    @Json(name = "geo_lat")
    val geoLat: String?,
    @Json(name = "geo_lon")
    val geoLon: String?,
    @Json(name = "house")
    val house: String?,
    @Json(name = "region")
    val region: String?,
    @Json(name = "street_with_type")
    val streetWithType: String?,
    @Json(name = "block")
    val block: String?
)