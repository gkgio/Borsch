package com.gkgio.borsch.onboarding

import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.navigation.Screens
import com.gkgio.domain.address.LoadAddressesUseCase
import com.gkgio.domain.onboarding.OnboardingVersionControllerUseCase
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class OnboardingViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val onboardingUseCase: OnboardingVersionControllerUseCase,
    private val loadAddressesUseCase: LoadAddressesUseCase,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    fun onGoToSecondPageClicked() {
        onboardingUseCase.saveActualVersion()

        loadAddressesUseCase
            .getLastSavedAddress()
            .applySchedulers()
            .subscribe({
                router.newRootScreen(Screens.MainFragmentScreen)
            }, {
                router.newRootScreen(Screens.LocationFragmentScreen(true))
            }).addDisposable()
    }
}