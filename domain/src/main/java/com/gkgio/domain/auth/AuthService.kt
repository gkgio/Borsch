package com.gkgio.domain.auth

import io.reactivex.Completable
import io.reactivex.Single

interface AuthService {
    fun getSmsCodeByPhone(inputPhone: String): Single<GetSmsCode>
    fun validateSmsCode(token: String, code: String): Single<ValidateSmsCode>
    fun sendPushToken(pushToken: String): Completable
    fun updateUserName(name: String): Single<User>
}