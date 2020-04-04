package com.gkgio.borsch.cookers.detail.meals

import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.domain.analytics.AnalyticsRepository
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class CookerMealViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    fun onMealClick(id: String, type: Int) {

    }
}