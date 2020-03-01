package com.gkgio.data.auth

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.auth.GetSmsCode
import javax.inject.Inject

class GetSmsCodeResponseTransformer @Inject constructor() :
    BaseTransformer<GetSmsCodeResponse, GetSmsCode> {

    override fun transform(data: GetSmsCodeResponse) = with(data) {
        GetSmsCode(
            token
        )
    }
}