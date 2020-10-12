package com.gkgio.data.auth

import com.gkgio.data.BaseTransformer
import com.gkgio.data.address.adding.AddressResponseTransformer
import com.gkgio.domain.auth.User
import javax.inject.Inject

class UserResponseTransformer @Inject constructor() : BaseTransformer<UserResponse, User> {

    override fun transform(data: UserResponse) = with(data) {
        User(
            id,
            banned,
            firstName,
            phone,
            avatarUrl,
            verified,
            suspended
        )
    }
}