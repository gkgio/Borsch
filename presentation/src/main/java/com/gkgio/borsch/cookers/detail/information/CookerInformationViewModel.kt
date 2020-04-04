package com.gkgio.borsch.cookers.detail.information

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.isNonInitialized
import com.gkgio.domain.analytics.AnalyticsRepository
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class CookerInformationViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()

    fun init(description: String?) {
        if (state.isNonInitialized()) {
            state.value = State(description)
        }
    }

    data class State(
        val description: String? = null
    )
}