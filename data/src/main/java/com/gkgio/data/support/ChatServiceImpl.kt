package com.gkgio.data.support

import com.gkgio.domain.support.SupportChatMessages
import com.gkgio.domain.support.ChatService
import com.gkgio.domain.support.MessageChat
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import javax.inject.Inject

class ChatServiceImpl @Inject constructor(
    private val chatServiceApi: ChatServiceApi,
    private val supportChatMessagesResponseTransformer: SupportChatMessagesResponseTransformer,
    private val messagesChatResponseTransformer: MessagesChatResponseTransformer
) : ChatService {

    override fun loadSupportMessages(): Single<SupportChatMessages> =
        chatServiceApi.loadSupportMessages()
            .map { supportChatMessagesResponseTransformer.transform(it) }

    override fun sendSupportMessage(text: String): Single<MessageChat> =
        chatServiceApi.sendSupportMessage(SupportChatMessageRequest(text))
            .map { messagesChatResponseTransformer.transform(it.message) }

    interface ChatServiceApi {
        @GET("support/messages")
        fun loadSupportMessages(): Single<SupportChatMessagesResponse>

        @POST("support/message")
        fun sendSupportMessage(@Body supportChatMessageRequest: SupportChatMessageRequest): Single<SupportChatMessageObjectResponse>
    }

}