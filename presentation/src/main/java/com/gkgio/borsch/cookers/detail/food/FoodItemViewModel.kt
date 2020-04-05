package com.gkgio.borsch.cookers.detail.food

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.cookers.CookersFragment.Companion.LUNCH_TYPE
import com.gkgio.borsch.cookers.CookersFragment.Companion.MEAL_TYPE
import com.gkgio.borsch.cookers.detail.*
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.ext.isNonInitialized
import com.gkgio.borsch.ext.nonNullValue
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.domain.cookers.LoadFoodItemUseCase
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class FoodItemViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val lunchUiTransformer: LunchUiTransformer,
    private val mealUiTransformer: MealUiTransformer,
    private val loadFoodItemUseCase: LoadFoodItemUseCase,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    lateinit var foodItemRequest: FoodItemRequest

    val state = MutableLiveData<State>()

    fun init(foodItemRequest: FoodItemRequest) {
        if (state.isNonInitialized()) {
            state.value = State(false)

            this.foodItemRequest = foodItemRequest
            Handler().postDelayed({ loadData() }, 300)
        }
    }

    fun loadData() {
        when (foodItemRequest.type) {
            LUNCH_TYPE -> loadLunch(foodItemRequest.cookerId, foodItemRequest.foodId)
            MEAL_TYPE -> loadMeal(foodItemRequest.cookerId, foodItemRequest.foodId)
        }
    }

    private fun loadLunch(cookerId: String, lunchId: String) {
        loadFoodItemUseCase
            .loadLunch(cookerId, lunchId)
            .map { lunchUiTransformer.transform(it) }
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
            .subscribe({
                state.value = state.nonNullValue.copy(lunchUi = it, isLoading = false)
            }, {
                state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
                processThrowable(it)
            }).addDisposable()
    }

    private fun loadMeal(cookerId: String, mealId: String) {
        loadFoodItemUseCase
            .loadMeal(cookerId, mealId)
            .map { mealUiTransformer.transform(it) }
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
            .subscribe({
                state.value = state.nonNullValue.copy(mealUi = it, isLoading = false)
            }, {
                state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
                processThrowable(it)
            }).addDisposable()
    }

    data class State(
        val isLoading: Boolean,
        val isInitialError: Boolean = false,
        val lunchUi: LunchUi? = null,
        val mealUi: MealUi? = null
    )
}