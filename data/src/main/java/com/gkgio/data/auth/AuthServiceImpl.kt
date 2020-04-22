package com.gkgio.data.auth

import com.gkgio.data.base.BaseService
import com.gkgio.data.exception.ServerExceptionTransformer
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
    private val validateSmsCodeResponseTransformer: ValidateSmsCodeResponseTransformer,
    serverExceptionTransformer: ServerExceptionTransformer
) : BaseService(serverExceptionTransformer), AuthService {

    override fun getSmsCodeByPhone(inputPhone: String): Single<GetSmsCode> = executeRequest(
        authServiceApi.getSmsCodeByPhone(GetSmsCodeRequest(inputPhone)).map {
            getSmsCodeResponseTransformer.transform(
                it
            )
        }
    )

    override fun validateSmsCode(token: String, code: String): Single<ValidateSmsCode> =
        executeRequest(
            authServiceApi.validateSmsCode(
                token,
                ValidateSmsCodeRequest(code.toInt())
            ).map { validateSmsCodeResponseTransformer.transform(it) }
        )

    override fun sendPushToken(pushToken: String): Completable =
        authServiceApi.sendPushTokenToServer(PushTokenRequest(pushToken))


    interface AuthServiceApi {
        @POST("auth/client")
        fun getSmsCodeByPhone(@Body getSmsCodeRequest: GetSmsCodeRequest): Single<GetSmsCodeResponse>

        @POST("auth/client/code")
        fun validateSmsCode(
            @Header("authorization") token: String,
            @Body validateSmsCodeRequest: ValidateSmsCodeRequest
        ): Single<ValidateSmsCodeResponse>

        @POST("misc/device_tokens/gcm")
        fun sendPushTokenToServer(@Body pushTokenRequest: PushTokenRequest): Completable
    }
}