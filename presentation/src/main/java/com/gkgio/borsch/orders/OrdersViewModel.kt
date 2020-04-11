package com.gkgio.borsch.orders

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.domain.analytics.AnalyticsRepository
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class OrdersViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state  = MutableLiveData<State>()
    init {

    }

    data class State(

    )
}