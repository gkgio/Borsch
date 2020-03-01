package com.gkgio.domain.auth

import io.reactivex.Single
import javax.inject.Inject

interface AuthUseCase {
    fun getSmsCodeByPhone(inputPhone: String): Single<GetSmsCode>
    fun validateSmsCode(token: String, code: String): Single<ValidateSmsCode>
}

class AuthUseCaseImpl @Inject constructor(
    private val authService: AuthService
) : AuthUseCase {

    override fun getSmsCodeByPhone(inputPhone: String): Single<GetSmsCode> =
        authService.getSmsCodeByPhone(inputPhone)

    override fun validateSmsCode(token: String, code: String): Single<ValidateSmsCode> =
        authService.validateSmsCode(token, code)
}