package com.gkgio.borsch.orders.chat

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.ext.isNonInitialized
import com.gkgio.borsch.ext.nonNullValue
import com.gkgio.borsch.support.SupportViewModel
import com.gkgio.domain.chats.MessageChat
import com.gkgio.domain.chats.OrderChatUseCase
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class OrderChatViewModel @Inject constructor(
    private val orderChatUseCase: OrderChatUseCase,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    private var disposable: Disposable? = null

    val state = MutableLiveData<State>()

    lateinit var orderId: String
    lateinit var userId: String

    fun init(orderId: String, userId: String) {
        if (state.isNonInitialized()) {
            this.orderId = orderId
            this.userId = userId

            state.value = State()
        }
    }

    fun loadMessages() {
        disposable?.dispose()

        disposable = orderChatUseCase
            .loadOrderChatMessages(orderId)
            .repeatWhen { Single.timer(15, TimeUnit.SECONDS).repeat() }
            .applySchedulers()
            .subscribe({
                state.value = state.nonNullValue.copy(chatMessages = it)
            }, {
                processThrowable(it)
            })
    }


    fun onSendMessageClick(text: String) {
        orderChatUseCase
            .sendOrderChatMessage(text, orderId)
            .applySchedulers()
            .subscribe({

            }, {
                processThrowable(it)
            })
            .addDisposable()
    }

    fun stopLoadMessagesOrder() {
        disposable?.dispose()
    }

    data class State(
        val chatMessages: List<MessageChat>? = null
    )
}