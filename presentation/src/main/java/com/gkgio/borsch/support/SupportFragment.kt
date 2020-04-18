package com.gkgio.borsch.support

import android.os.Bundle
import android.text.InputType
import android.util.TypedValue
import android.view.View
import androidx.core.view.isVisible
import com.github.bassaer.chatmessageview.model.Message
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.createViewModel
import com.gkgio.borsch.ext.getColorCompat
import com.gkgio.borsch.ext.observeValue
import kotlinx.android.synthetic.main.fragment_support.*
import org.joda.time.DateTime
import java.util.*

class SupportFragment : BaseFragment<SupportViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_support

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.supportViewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMessages()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setChatViewStyle()

        viewModel.state.observeValue(this) { state ->
            errorViewSupport.isVisible = state.isInitialError
            chatView.isVisible = !state.isInitialError

            state.supportChatMessages?.messageList?.let { loadedMessageList ->
                val listMessages = mutableListOf<Message>()
                val loadedMessageListReversed = loadedMessageList.reversed()
                loadedMessageListReversed.forEach {
                    val message = Message.Builder()
                        .setRight(it.toSupport)
                        .setSendTime(DateTime(it.sentAt).toCalendar(Locale.getDefault()))
                        .setType(Message.Type.TEXT)
                        .setUser(ChatUserUi(if (it.toSupport) "1" else "2", null))
                        .setText(it.text)
                        .build()
                    listMessages.add(message)
                }

                val messageView = chatView.getMessageView()
                messageView.removeAll()
                messageView.init(listMessages)
                messageView.setSelection(messageView.count - 1)
            }
        }

        chatView.setOnClickSendButtonListener(View.OnClickListener {
            val messageText = chatView.inputText
            if (messageText.isNotBlank()) {
                viewModel.onSendMessageClick(messageText)

                val message = Message.Builder()
                    .setRight(true)
                    .setType(Message.Type.TEXT)
                    .setUser(ChatUserUi("1", null))
                    .setText(messageText)
                    .build()

                chatView.send(message)
                chatView.inputText = ""
            }
        })
    }

    private fun setChatViewStyle() {
        chatView.setRightBubbleColor(requireContext().getColorCompat(R.color.blue))
        chatView.setLeftBubbleColor(requireContext().getColorCompat(R.color.grey_bg))
        chatView.setBackgroundColor(requireContext().getColorCompat(R.color.white))
        chatView.setSendButtonColor(requireContext().getColorCompat(R.color.blue))
        chatView.setRightMessageTextColor(requireContext().getColorCompat(R.color.white))
        chatView.setLeftMessageTextColor(requireContext().getColorCompat(R.color.black))
        chatView.setUsernameTextColor(requireContext().getColorCompat(R.color.black))
        chatView.setSendTimeTextColor(requireContext().getColorCompat(R.color.black))
        chatView.setInputTextHint("Новое сообщение")
        chatView.setMessageMarginTop(6)
        chatView.setMessageMarginBottom(6)
        chatView.setMaxInputLine(5)
        chatView.setUsernameFontSize(resources.getDimension(R.dimen.font_small))
        chatView.inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        chatView.inputTextColor = requireContext().getColorCompat(R.color.black)
        chatView.setInputTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopLoadMessagesSupport()
    }
}