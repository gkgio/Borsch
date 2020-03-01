package com.gkgio.data.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ValidateSmsCodeRequest(
    @Json(name = "sms_code")
    val code: Int
)