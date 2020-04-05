package com.gkgio.borsch.cookers.detail

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.cookers.CookersViewModel
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.ext.isNonInitialized
import com.gkgio.borsch.ext.nonNullValue
import com.gkgio.borsch.utils.SingleLiveEvent
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.domain.cookers.Cooker
import com.gkgio.domain.cookers.LoadCookersUseCase
import com.gkgio.domain.cookers.detail.CookerDetail
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class CookerDetailViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val cookersUseCase: LoadCookersUseCase,
    private val cookerDetailUiTransformer: CookerDetailUiTransformer,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()
    val openFoodItem = SingleLiveEvent<FoodItemRequest>()

    fun init(cookerId: String, foodId: String?, type: Int?) {
        if (state.isNonInitialized()) {
            state.value = State(false)

            cookersUseCase
                .loadCookerDetail(cookerId)
                .map { cookerDetailUiTransformer.transform(it) }
                .applySchedulers()
                .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
                .subscribe({
                    state.value = state.nonNullValue.copy(isLoading = false, cookerDetail = it)
                    if (foodId != null && type != null) {
                        Handler().postDelayed(
                            {
                                openFoodItem.value = FoodItemRequest(
                                    cookerId, foodId, type
                                )
                            },
                            300
                        )
                    }
                }, {
                    state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
                    processThrowable(it)
                }).addDisposable()
        }
    }

    fun onMealClick(cookerId: String, foodId: String, type: Int) {
        openFoodItem.value = FoodItemRequest(cookerId, foodId, type)
    }

    data class State(
        val isLoading: Boolean,
        val isInitialError: Boolean = false,
        val cookerDetail: CookerDetailUi? = null
    )
}