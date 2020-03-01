package com.gkgio.borsch.auth

import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.navigation.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class InputPhoneViewModel @Inject constructor(
    private val router: Router,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    fun onSendCodeBtnClick(inputPhone: String?) {
        if (inputPhone != null) {
            router.navigateTo(Screens.ValidatePhoneFragmentScreen(inputPhone))
        }
    }
}