package com.gkgio.data.support

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SupportChatMessageObjectResponse(
    @Json(name = "message")
    val message: MessageChatResponse
)