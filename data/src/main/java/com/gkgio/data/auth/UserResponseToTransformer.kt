package com.gkgio.data.auth

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.auth.User
import javax.inject.Inject

class UserResponseToTransformer @Inject constructor() :
    BaseTransformer<User, UserResponse> {

    override fun transform(data: User) = with(data) {
        UserResponse(
            banned,
            firstName,
            phone,
            avatarUrl,
            id
        )
    }
}