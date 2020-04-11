package com.gkgio.data.auth

import com.gkgio.data.address.adding.AddressResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "banned")
    val banned: Boolean,
    @Json(name = "firstName")
    val firstName: String?,
    @Json(name = "phone")
    val phone: String?,
    @Json(name = "avatar_url")
    val avatarUrl: String?
)