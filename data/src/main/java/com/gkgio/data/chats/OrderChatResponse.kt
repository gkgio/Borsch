package com.gkgio.data.chats

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderChatResponse(
    @Json(name = "messages")
    val messageList: List<MessageChatResponse>?
)