package com.gkgio.domain.support

import com.gkgio.domain.auth.User

data class SupportChatMessages(
    val messageList: List<MessageChat>?,
    val userResponse: User
)