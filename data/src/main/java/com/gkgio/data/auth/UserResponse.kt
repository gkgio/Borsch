package com.gkgio.data.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "addresses")
    val addresses: List<String>?,
    @Json(name = "banned")
    val banned: Boolean,
    @Json(name = "firstName")
    val firstName: String?,
    @Json(name = "phone")
    val phone: String?,
    @Json(name = "avatar_url")
    val avatarUrl: String?
)