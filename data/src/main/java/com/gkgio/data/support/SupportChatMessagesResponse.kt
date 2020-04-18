package com.gkgio.data.support

import com.gkgio.data.auth.UserResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SupportChatMessagesResponse(
    @Json(name = "messages")
    val messageList: List<MessageChatResponse>?,
    @Json(name = "user")
    val userResponse: UserResponse
)
