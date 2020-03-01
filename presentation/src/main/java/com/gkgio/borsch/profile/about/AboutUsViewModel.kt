package com.gkgio.borsch.profile.about

import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class AboutUsViewModel @Inject constructor(
    private val router: Router,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

}