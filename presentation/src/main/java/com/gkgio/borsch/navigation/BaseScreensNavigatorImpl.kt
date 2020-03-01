package com.gkgio.borsch.navigation

import com.gkgio.borsch.base.BaseScreensNavigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppScreen

open class BaseScreensNavigatorImpl constructor(
    private val router: Router
) : BaseScreensNavigator {

    var screen: SupportAppScreen? = null

    override fun navigateBack() = router.exit()

    override fun navigateToMarket(packageName: String) =
        router.navigateTo(Screens.MarketScreen(packageName))

    override fun navigateBackTo() = router.backTo(screen)
}