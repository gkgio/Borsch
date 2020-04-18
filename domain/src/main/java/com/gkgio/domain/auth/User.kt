package com.gkgio.domain.auth


data class User(
    val id: String,
    val banned: Boolean,
    val firstName: String?,
    val phone: String?,
    val avatarUrl: String?
)