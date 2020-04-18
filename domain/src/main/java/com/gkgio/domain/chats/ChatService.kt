package com.gkgio.domain.chats

import io.reactivex.Single

interface ChatService {
    fun loadSupportMessages(): Single<SupportChatMessages>
    fun sendSupportMessage(text: String): Single<MessageChat>
    fun loadOrderChatMessages(orderId: String): Single<List<MessageChat>>
    fun sendOrderChatMessage(text: String, orderId: String): Single<MessageChat>
}