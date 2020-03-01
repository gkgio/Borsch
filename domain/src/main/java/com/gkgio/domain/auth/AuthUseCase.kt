package com.gkgio.domain.auth

import io.reactivex.Single
import javax.inject.Inject

interface AuthUseCase {
    fun getSmsCodeByPhone(inputPhone: String): Single<GetSmsCode>
    fun validateSmsCode(token: String, code: String): Single<ValidateSmsCode>
    fun getAuthToken(): String?
    fun saveAuthToken(token: String)
}

class AuthUseCaseImpl @Inject constructor(
    private val authService: AuthService,
    private val authRepository: AuthRepository
) : AuthUseCase {

    override fun getSmsCodeByPhone(inputPhone: String): Single<GetSmsCode> =
        authService.getSmsCodeByPhone(inputPhone)

    override fun validateSmsCode(token: String, code: String): Single<ValidateSmsCode> =
        authService.validateSmsCode(token, code)

    override fun getAuthToken(): String? =
        authRepository.getAuthToken()

    override fun saveAuthToken(token: String) =
        authRepository.saveAuthToken(token)
}