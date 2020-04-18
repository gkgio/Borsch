package com.gkgio.data.support

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.support.MessageChat
import javax.inject.Inject

class MessagesChatResponseTransformer @Inject constructor() :
    BaseTransformer<MessageChatResponse, MessageChat> {

    override fun transform(data: MessageChatResponse) = with(data) {
        MessageChat(
            from,
            fromSupport,
            icon,
            id,
            orderId,
            sentAt,
            system,
            systemStatus,
            text,
            timestamp,
            title,
            to,
            toSupport
        )
    }
}