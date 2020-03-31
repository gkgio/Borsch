package com.gkgio.borsch.main

import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.domain.theme.ThemeRepository
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.navigation.Screens
import com.gkgio.borsch.utils.SingleLiveEvent
import com.gkgio.domain.address.LoadAddressesUseCase
import com.gkgio.domain.onboarding.OnboardingVersionControllerUseCase
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class LaunchViewModel @Inject constructor(
    private val router: Router,
    private val themeRepository: ThemeRepository,
    private val onboardingVersionControllerUseCase: OnboardingVersionControllerUseCase,
    private val loadAddressesUseCase: LoadAddressesUseCase,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    fun onNewStart() {
        loadAddressesUseCase
            .getLastSavedAddress()
            .applySchedulers()
            .subscribe({
                if (onboardingVersionControllerUseCase.isSavedVersionActual()) {
                    router.newRootScreen(Screens.MainFragmentScreen)
                } else {
                    router.newRootScreen(Screens.OnboardingFragmentScreen)
                }
            }, {
                router.newRootScreen(Screens.OnboardingFragmentScreen)
            }).addDisposable()
    }
}