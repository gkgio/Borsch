package com.gkgio.domain.auth

interface AuthRepository {
    fun getAuthToken(): String?
    fun saveAuthToken(token: String)
}