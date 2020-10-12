package com.gkgio.data.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SmsResponse(
    @Json(name = "id")
    val tmpToken: String
)