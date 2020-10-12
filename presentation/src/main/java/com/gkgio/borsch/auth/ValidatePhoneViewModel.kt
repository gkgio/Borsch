package com.gkgio.borsch.auth

import android.os.CountDownTimer
import android.os.SystemClock
import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.ext.isNonInitialized
import com.gkgio.borsch.ext.nonNullValue
import com.gkgio.borsch.navigation.Screens
import com.gkgio.borsch.utils.events.OrderChangeEvent
import com.gkgio.borsch.utils.events.UserProfileChanged
import com.gkgio.domain.address.LoadAddressesUseCase
import com.gkgio.domain.auth.AuthUseCase
import ru.terrakok.cicerone.Router
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ValidatePhoneViewModel @Inject constructor(
    private val router: Router,
    private val authUseCase: AuthUseCase,
    private val userProfileChanged: UserProfileChanged,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()
    val countDownSmsTimer = MutableLiveData<Long>()

    private var countDownTimer: CountDownTimer? = null
    private lateinit var phone: String
    private lateinit var tmpToken: String

    fun init(tmpToken: String, phone: String) {
        if (state.isNonInitialized()) {
            state.value = State()

            this.tmpToken = tmpToken
            this.phone = phone
            startTimer()
        }
    }

    private fun requestSmsCode() {
        authUseCase
            .getSmsCodeByPhone(phone)
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isProgress = true) }
            .subscribe({
                state.value = state.nonNullValue.copy(isProgress = false)
                tmpToken = it
                startTimer()
            }, {
                state.value = state.nonNullValue.copy(isProgress = false, isInitialError = true)
                processThrowable(it)
            }).addDisposable()
    }

    fun onSmsCodeFullInput(smsCode: String) {
        authUseCase
            .validateSmsCode(tmpToken, smsCode)
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isProgress = true) }
            .subscribe({
                state.value = state.nonNullValue.copy(isProgress = false)
                userProfileChanged.onComplete("")
                router.backTo(Screens.MainFragmentScreen)
            }, { throwable ->
                state.value = state.nonNullValue.copy(isProgress = false)
                processThrowable(throwable)
            }).addDisposable()

    }

    fun onUpdateAfterErrorClick() {
        countDownTimer?.cancel()
        requestSmsCode()
    }

    fun onResendSmsClick() {
        if (countDownSmsTimer.nonNullValue == 0L) {
            countDownTimer?.cancel()
            requestSmsCode()
        }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(120000, 1000) {
            override fun onFinish() {

            }

            override fun onTick(millisUntilFinished: Long) {
                countDownSmsTimer.postValue(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished))
            }

        }.start()
    }

    data class State(
        val isProgress: Boolean = false,
        val isInitialError: Boolean = false
    )
}