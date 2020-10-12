package com.gkgio.data.auth

import com.gkgio.data.base.BaseService
import com.gkgio.data.exception.ServerExceptionTransformer
import com.gkgio.domain.auth.AuthService
import com.gkgio.domain.auth.User
import com.gkgio.domain.auth.ValidateSmsCode
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    private val authServiceApi: AuthServiceApi,
    private val validateSmsCodeResponseTransformer: ValidateSmsCodeResponseTransformer,
    private val userResponseTransformer: UserResponseTransformer,
    serverExceptionTransformer: ServerExceptionTransformer
) : BaseService(serverExceptionTransformer), AuthService {

    override fun getSmsCodeByPhone(inputPhone: String): Single<String> = executeRequest(
        authServiceApi.getSmsCodeByPhone(inputPhone).map { it.tmpToken }
    )

    override fun validateSmsCode(token: String, code: String): Single<ValidateSmsCode> =
        executeRequest(
            authServiceApi.validateSmsCode(
                token,
                code
            ).map { validateSmsCodeResponseTransformer.transform(it) }
        )

    override fun sendPushToken(pushToken: String): Completable =
        authServiceApi.sendPushTokenToServer(PushTokenRequest(pushToken))

    override fun updateUserName(name: String): Single<User> =
        authServiceApi.updateUserName(UpdateNameRequest(name))
            .map { userResponseTransformer.transform(it.user) }


    interface AuthServiceApi {
        @POST("client/auth")
        fun getSmsCodeByPhone(@Query("phone") phone: String): Single<SmsResponse>

        @PATCH("client/auth/{tmpToken}")
        fun validateSmsCode(
            @Path("tmpToken") tmpToken: String,
            @Query("code") code: String
        ): Single<ValidateSmsCodeResponse>

        @POST("misc/device_tokens/fcm")
        fun sendPushTokenToServer(@Body pushTokenRequest: PushTokenRequest): Completable

        @POST("client/add_name")
        fun updateUserName(@Body updateNameRequest: UpdateNameRequest): Single<UpdateNameResponse>
    }
}