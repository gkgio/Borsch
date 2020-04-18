package com.gkgio.data.chats

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageChatRequest(
    @Json(name = "text")
    val text: String
)