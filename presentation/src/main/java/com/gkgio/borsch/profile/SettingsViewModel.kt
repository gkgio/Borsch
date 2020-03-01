package com.gkgio.borsch.profile

import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.navigation.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    fun onAuthBtnClick() {
        router.navigateTo(Screens.InputPhoneFragmentScreen)
    }
}