package com.gkgio.borsch.cookers

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.ext.isNonInitialized
import com.gkgio.borsch.ext.nonNullValue
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.domain.cookers.Cooker
import com.gkgio.domain.cookers.CookersRequest
import com.gkgio.domain.cookers.LoadCookersUseCase
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class CookersViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val loadCookersUseCase: LoadCookersUseCase,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()

    init {
        if (state.isNonInitialized()) {
            state.value = State(false)

            loadCookersUseCase
                .loadCookersList(CookersRequest())
                .applySchedulers()
                .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
                .subscribe({
                    state.value = state.nonNullValue.copy(isLoading = false)
                }, {
                    state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
                }).addDisposable()

        }
    }

    data class State(
        val isLoading: Boolean,
        val isInitialError: Boolean = false,
        val cookers: List<Cooker>? = null
    )
}