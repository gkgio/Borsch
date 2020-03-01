package com.gkgio.data.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetSmsCodeRequest(
    @Json(name = "phone")
    val phone: String
)