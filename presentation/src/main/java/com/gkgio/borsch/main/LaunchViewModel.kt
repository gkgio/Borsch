package com.gkgio.borsch.main

import com.gkgio.domain.theme.ThemeRepository
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.navigation.Screens
import com.gkgio.borsch.utils.SingleLiveEvent
import com.gkgio.domain.onboarding.OnboardingVersionControllerUseCase
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class LaunchViewModel @Inject constructor(
    private val router: Router,
    private val themeRepository: ThemeRepository,
    private val onboardingVersionControllerUseCase: OnboardingVersionControllerUseCase
) : BaseViewModel() {

    fun onNewStart() {
        if (onboardingVersionControllerUseCase.isSavedVersionActual()) {
            router.newRootScreen(Screens.MainFragmentScreen)
        } else {
            router.newRootScreen(Screens.OnboardingFragmentScreen)
        }
    }
}