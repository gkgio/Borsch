package com.gkgio.borsch.support

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.ext.isNonInitialized
import com.gkgio.borsch.ext.nonNullValue
import com.gkgio.borsch.navigation.Screens
import com.gkgio.domain.auth.AuthRepository
import com.gkgio.domain.chats.SupportChatMessages
import com.gkgio.domain.chats.SupportChatUseCase
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SupportViewModel @Inject constructor(
    private val supportChatUseCase: SupportChatUseCase,
    private val authRepository: AuthRepository,
    private val router: Router,
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
                processThrowable(it)
            })
            .addDisposable()
    }

    fun stopLoadMessagesSupport() {
        disposable?.dispose()
    }

    fun onAuthBtnClick() {
        router.navigateTo(Screens.InputPhoneFragmentScreen)
    }

    data class State(
        val isInitialError: Boolean = false,
        val supportChatMessages: SupportChatMessages? = null
    )
}