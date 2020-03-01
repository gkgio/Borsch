package com.gkgio.borsch.onboarding

import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.navigation.Screens
import com.gkgio.domain.onboarding.OnboardingVersionControllerUseCase
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class OnboardingViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val onboardingUseCase: OnboardingVersionControllerUseCase
) : BaseViewModel() {

    fun onGoToSecondPageClicked() {
        onboardingUseCase.saveActualVersion()
        router.newRootScreen(Screens.MainFragmentScreen)
    }
}