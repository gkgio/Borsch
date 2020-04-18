package com.gkgio.borsch.support

import android.graphics.Bitmap
import com.github.bassaer.chatmessageview.model.IChatUser

data class ChatUserUi(
    private val id: String,
    private val name: String?
) : IChatUser {
    override fun getIcon(): Bitmap? = null

    override fun getId(): String = id

    override fun getName(): String? = name

    override fun setIcon(bmp: Bitmap) {

    }
}