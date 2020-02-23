package com.gkgio.domain.auth

interface AuthRepository {
    fun getAuthToken(timestamp: String): String?
}