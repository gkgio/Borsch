package com.gkgio.data.auth

import android.content.SharedPreferences
import com.gkgio.domain.auth.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val prefs: SharedPreferences
) : AuthRepository {

    private companion object {
        private const val AUTH_TOKEN = "auth_token"
    }

    override fun getAuthToken(): String? =
        prefs.getString(AUTH_TOKEN, null)

    override fun saveAuthToken(token: String) {
        prefs.edit().putString(AUTH_TOKEN, token).apply()
    }
}