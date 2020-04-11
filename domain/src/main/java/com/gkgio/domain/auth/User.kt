package com.gkgio.domain.auth

import com.gkgio.domain.address.Address

data class User(
    val banned: Boolean,
    val firstName: String?,
    val phone: String?,
    val avatarUrl: String?
)