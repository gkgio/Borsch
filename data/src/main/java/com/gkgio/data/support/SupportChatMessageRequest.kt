package com.gkgio.data.support

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SupportChatMessageRequest(
    @Json(name = "text")
    val text: String
)