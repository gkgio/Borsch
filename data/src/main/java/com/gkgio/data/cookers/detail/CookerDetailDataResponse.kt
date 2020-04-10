package com.gkgio.data.cookers.detail

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CookerDetailDataResponse(
    @Json(name = "cooker")
    val cooker: CookerDetailResponse
)