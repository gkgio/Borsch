package com.gkgio.data.chats

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageChatObjectResponse(
    @Json(name = "message")
    val message: MessageChatResponse
)