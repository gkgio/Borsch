package com.gkgio.data.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateNameResponse(
    @Json(name = "user")
    val user: UserResponse
)