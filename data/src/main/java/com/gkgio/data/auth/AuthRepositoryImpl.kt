package com.gkgio.data.auth

import okhttp3.internal.and
import com.gkgio.domain.auth.AuthRepository
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    private companion object {
        private const val X_AUTH_PASS = "+HG%3M^VLKG-T6UO+MV="
    }

    override fun getAuthToken(timestamp: String): String? {
        return getMD5(timestamp + X_AUTH_PASS)
    }

    private fun getMD5(source: String): String? {
        return try {
            val messageDigest = MessageDigest.getInstance("MD5")
            messageDigest.update(source.toByteArray())

            val digest = messageDigest.digest()
            val stringBuilder = StringBuilder()

            digest.forEach { stringBuilder.append(String.format("%02x", it.and(0xff))) }

            stringBuilder.toString()
        } catch (e: NoSuchAlgorithmException) {
            // TODO логировать ошибку в ремоут
            null
        }
    }
}