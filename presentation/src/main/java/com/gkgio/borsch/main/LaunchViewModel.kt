package com.gkgio.borsch.main

import android.content.Intent
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.domain.theme.ThemeRepository
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.navigation.Screens
import com.gkgio.borsch.utils.SingleLiveEvent
import com.gkgio.domain.address.LoadAddressesUseCase
import com.gkgio.domain.auth.AuthUseCase
import com.gkgio.domain.onboarding.OnboardingVersionControllerUseCase
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class LaunchViewModel @Inject constructor(
    private val router: Router,
    private val themeRepository: ThemeRepository,
    private val onboardingVersionControllerUseCase: OnboardingVersionControllerUseCase,
    private val loadAddressesUseCase: LoadAddressesUseCase,
    private val authUseCase: AuthUseCase,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val checkDeepLing = SingleLiveEvent<Intent>()

    fun init(intent: Intent?) {
        loadAddressesUseCase
            .getLastSavedAddress()
            .applySchedulers()
            .subscribe({
                if (onboardingVersionControllerUseCase.isSavedVersionActual()) {
                    router.newRootScreen(Screens.MainFragmentScreen)
                    if (intent != null) {
                        checkDeepLing.value = intent
                    }
                } else {
                    router.newRootScreen(Screens.OnboardingFragmentScreen)
                }
            }, {
                router.newRootScreen(Screens.OnboardingFragmentScreen)
            }).addDisposable()
    }

    fun onOpenOrderChatDeepLink(orderId: String) {
        val userId = authUseCase.loadUserProfile()?.id
        if (userId != null) {
            router.switchTo(R.id.tab_orders)
            router.navigateTo(Screens.OrderChatFragmentScreen(orderId, userId))
        }
    }

    fun onOpenSupportChatDeepLink() {
        router.switchTo(R.id.tab_support)
    }
}