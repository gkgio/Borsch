package com.gkgio.domain.support

data class MessageChat(
    val from: String?,
    val fromSupport: Boolean,
    val icon: String?,
    val id: String,
    val orderId: String?,
    val sentAt: String,
    val system: Boolean,
    val systemStatus: String?,
    val text: String,
    val timestamp: Long,
    val title: String?,
    val to: String?,
    val toSupport: Boolean
)