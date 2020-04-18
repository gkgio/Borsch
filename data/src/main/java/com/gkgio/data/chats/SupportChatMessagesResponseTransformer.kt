package com.gkgio.data.chats

import com.gkgio.data.BaseTransformer
import com.gkgio.data.auth.UserResponseTransformer
import com.gkgio.domain.chats.SupportChatMessages
import javax.inject.Inject

class SupportChatMessagesResponseTransformer @Inject constructor(
    private val messagesChatResponseTransformer: MessagesChatResponseTransformer,
    private val userResponseTransformer: UserResponseTransformer
) : BaseTransformer<SupportChatMessagesResponse, SupportChatMessages> {

    override fun transform(data: SupportChatMessagesResponse) = with(data) {
        SupportChatMessages(
            if (!messageList.isNullOrEmpty()) messageList.map {
                messagesChatResponseTransformer.transform(it)
            } else {
                null
            },
            userResponseTransformer.transform(userResponse)
        )
    }
}