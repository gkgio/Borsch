package com.gkgio.domain.address

data class GeoSuggestionData(
    val city: String?,
    val city_area: String?,
    val city_district: String?,
    val city_type_full: String?,
    val country: String?,
    val flat: String?,
    val geo_lat: String,
    val geo_lon: String,
    val house: String?,
    val region: String?
)