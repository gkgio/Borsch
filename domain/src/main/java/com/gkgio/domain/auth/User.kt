package com.gkgio.domain.auth

data class User(
    val addresses: List<String>?,
    val banned: Boolean,
    val firstName: String?,
    val phone: String?,
    val avatarUrl: String?
)