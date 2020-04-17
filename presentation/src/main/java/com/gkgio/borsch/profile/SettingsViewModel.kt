package com.gkgio.borsch.profile

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.ext.isNonInitialized
import com.gkgio.borsch.ext.nonNullValue
import com.gkgio.borsch.navigation.Screens
import com.gkgio.borsch.utils.events.UserProfileChanged
import com.gkgio.domain.auth.AuthRepository
import com.gkgio.domain.auth.AuthUseCase
import com.gkgio.domain.auth.User
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val authUseCase: AuthUseCase,
    private val authRepository: AuthRepository,
    private val userProfileChanged: UserProfileChanged,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()

     init {
        if (state.isNonInitialized()) {
            state.value = State()

            loadUser()

            userProfileChanged
                .getEventResult()
                .applySchedulers()
                .subscribe({
                    loadUser()
                }, {
                    Timber.e(it)
                }).addDisposable()

        }
    }

    private fun loadUser() {
        state.value = state.nonNullValue.copy(user = authUseCase.loadUserProfile())
    }

    fun onExitBtnClick() {
        authRepository
            .removeAccountData()
            .applySchedulers()
            .subscribe({
                state.value = state.nonNullValue.copy(user = null)
            }, {
                Timber.e(it)
            }).addDisposable()
    }

    fun onAuthBtnClick() {
        router.navigateTo(Screens.InputPhoneFragmentScreen)
    }

    data class State(
        val user: User? = null
    )
}