package com.gkgio.data.chats

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageChatResponse(
    @Json(name = "from")
    val from: String?,
    @Json(name = "from_support")
    val fromSupport: Boolean,
    @Json(name = "icon")
    val icon: String?,
    @Json(name = "id")
    val id: String,
    @Json(name = "order_id")
    val orderId: String?,
    @Json(name = "sent_at")
    val sentAt: String,
    @Json(name = "system")
    val system: Boolean,
    @Json(name = "system_status")
    val systemStatus: Boolean?,
    @Json(name = "text")
    val text: String,
    @Json(name = "timestamp")
    val timestamp: Long?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "to")
    val to: String?,
    @Json(name = "to_support")
    val toSupport: Boolean
)