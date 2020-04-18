package com.gkgio.data.support

import com.gkgio.data.BaseTransformer
import com.gkgio.data.auth.UserResponseTransformer
import com.gkgio.data.cookers.LunchResponse
import com.gkgio.data.cookers.MealResponseTransformer
import com.gkgio.domain.cookers.Lunch
import com.gkgio.domain.support.SupportChatMessages
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