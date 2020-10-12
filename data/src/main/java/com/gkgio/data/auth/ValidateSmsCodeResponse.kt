package com.gkgio.data.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ValidateSmsCodeResponse(
    @Json(name = "jwt_token")
    val token: String,
    @Json(name = "client")
    val user: UserResponse
)