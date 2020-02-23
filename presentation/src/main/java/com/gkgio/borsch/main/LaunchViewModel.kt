package com.gkgio.borsch.main

import com.gkgio.domain.theme.ThemeRepository
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.navigation.Screens
import com.gkgio.borsch.utils.SingleLiveEvent
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class LaunchViewModel @Inject constructor(
    private val router: Router,
    private val themeRepository: ThemeRepository
    ) : BaseViewModel() {


    fun onNewStart() {
        router.newRootScreen(Screens.OnboardingFragmentScreen)
    }
}