package com.gkgio.domain.chats

import com.gkgio.domain.auth.User

data class SupportChatMessages(
    val messageList: List<MessageChat>?,
    val userResponse: User
)