package com.gkgio.data.auth

import com.gkgio.domain.auth.AuthService
import com.gkgio.domain.auth.GetSmsCode
import com.gkgio.domain.auth.ValidateSmsCode
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    private val authServiceApi: AuthServiceApi,
    private val getSmsCodeResponseTransformer: GetSmsCodeResponseTransformer,
    private val validateSmsCodeResponseTransformer: ValidateSmsCodeResponseTransformer
) : AuthService {

    override fun getSmsCodeByPhone(inputPhone: String): Single<GetSmsCode> =
        authServiceApi.getSmsCodeByPhone(GetSmsCodeRequest(inputPhone)).map {
            getSmsCodeResponseTransformer.transform(
                it
            )
        }

    override fun validateSmsCode(token: String, code: String): Single<ValidateSmsCode> =
        authServiceApi.validateSmsCode(
            token,
            ValidateSmsCodeRequest(code.toInt())
        ).map { validateSmsCodeResponseTransformer.transform(it) }


    interface AuthServiceApi {
        @POST("api/auth/client")
        fun getSmsCodeByPhone(@Body getSmsCodeRequest: GetSmsCodeRequest): Single<GetSmsCodeResponse>

        @POST("api/auth/client/code")
        fun validateSmsCode(
            @Header("authorization") token: String,
            @Body validateSmsCodeRequest: ValidateSmsCodeRequest
        ): Single<ValidateSmsCodeResponse>
    }
}