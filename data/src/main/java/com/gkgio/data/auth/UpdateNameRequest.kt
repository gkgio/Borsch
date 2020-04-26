package com.gkgio.data.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateNameRequest(
    @Json(name = "first_name")
    val name: String
)