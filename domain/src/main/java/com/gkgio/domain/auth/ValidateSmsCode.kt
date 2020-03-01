package com.gkgio.domain.auth

data class ValidateSmsCode(
    val token: String,
    val user: User
)