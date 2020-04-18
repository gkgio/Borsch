package com.gkgio.borsch.support

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.ext.isNonInitialized
import com.gkgio.borsch.ext.nonNullValue
import com.gkgio.domain.auth.AuthRepository
import com.gkgio.domain.support.SupportChatMessages
import com.gkgio.domain.support.SupportChatUseCase
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SupportViewModel @Inject constructor(
    private val supportChatUseCase: SupportChatUseCase,
    private val authRepository: AuthRepository,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    var disposable: Disposable? = null
    val state = MutableLiveData<State>()

    init {
        if (state.isNonInitialized()) {
            state.value = State()
        }
    }

    fun loadMessages() {
        disposable?.dispose()
        if (authRepository.getAuthToken() != null) {
            state.value = state.nonNullValue.copy(isInitialError = false)

            disposable = supportChatUseCase
                .loadSupportMessages()
                .repeatWhen { Single.timer(15, TimeUnit.SECONDS).repeat() }
                .applySchedulers()
                .subscribe({
                    state.value = state.nonNullValue.copy(
                        supportChatMessages = it,
                        isInitialError = false
                    )
                }, {
                    processThrowable(it)
                })
        } else {
            state.value = state.nonNullValue.copy(isInitialError = true)
        }
    }

    fun onSendMessageClick(text: String) {
        supportChatUseCase
            .sendSupportMessage(text)
            .applySchedulers()
            .subscribe({

            }, {
                Timber.e(it)
            })
            .addDisposable()
    }

    fun stopLoadMessagesSupport() {
        disposable?.dispose()
    }


    data class State(
        val isInitialError: Boolean = false,
        val supportChatMessages: SupportChatMessages? = null
    )
}