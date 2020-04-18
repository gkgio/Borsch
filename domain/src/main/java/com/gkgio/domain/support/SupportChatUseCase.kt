package com.gkgio.domain.support

import io.reactivex.Single
import javax.inject.Inject

interface SupportChatUseCase {
    fun loadSupportMessages(): Single<SupportChatMessages>
    fun sendSupportMessage(text: String): Single<MessageChat>
}

class SupportChatUseCaseImpl @Inject constructor(
    private val supportChatService: ChatService
) : SupportChatUseCase {

    override fun loadSupportMessages(): Single<SupportChatMessages> =
        supportChatService.loadSupportMessages()

    override fun sendSupportMessage(text: String): Single<MessageChat> =
        supportChatService.sendSupportMessage(text)
}