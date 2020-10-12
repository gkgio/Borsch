package com.gkgio.borsch.auth

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.ext.nonNullValue
import com.gkgio.borsch.navigation.Screens
import com.gkgio.domain.auth.AuthUseCase
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class InputPhoneViewModel @Inject constructor(
    private val router: Router,
    private val authUseCase: AuthUseCase,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()

    init {
        state.value = State()
    }

    fun onSendCodeBtnClick(inputPhone: String?) {
        inputPhone?.let {
            requestSmsCode(it)
        }
    }

    fun onUpdateAfterErrorClick(inputPhone: String?) {
        inputPhone?.let {
            requestSmsCode(it)
        }
    }

    private fun requestSmsCode(phone: String) {
        authUseCase
            .getSmsCodeByPhone(phone)
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isProgress = true) }
            .subscribe({ tmpToken ->
                state.value = state.nonNullValue.copy(isProgress = false, isInitialError = false)
                router.navigateTo(
                    Screens.ValidatePhoneFragmentScreen(
                        tmpToken,
                        phone
                    )
                )
            }, {
                state.value = state.nonNullValue.copy(isProgress = false, isInitialError = true)
                processThrowable(it)
            }).addDisposable()
    }

    data class State(
        val isProgress: Boolean = false,
        val isInitialError: Boolean = false
    )
}