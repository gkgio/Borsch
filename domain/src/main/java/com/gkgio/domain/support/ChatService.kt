package com.gkgio.domain.support

import io.reactivex.Single

interface ChatService {
    fun loadSupportMessages(): Single<SupportChatMessages>
    fun sendSupportMessage(text: String): Single<MessageChat>
}