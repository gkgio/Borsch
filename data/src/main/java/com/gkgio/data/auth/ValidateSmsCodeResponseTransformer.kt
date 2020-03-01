package com.gkgio.data.auth

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.auth.ValidateSmsCode
import javax.inject.Inject

class ValidateSmsCodeResponseTransformer @Inject constructor(
    private val userResponseTransformer: UserResponseTransformer
) : BaseTransformer<ValidateSmsCodeResponse, ValidateSmsCode> {

    override fun transform(data: ValidateSmsCodeResponse) = with(data) {
        ValidateSmsCode(
            token,
            userResponseTransformer.transform(user)
        )
    }
}