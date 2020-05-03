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
import com.gkgio.borsch.utils.PriceFormatter
import com.gkgio.borsch.utils.SingleLiveEvent
import com.gkgio.borsch.utils.events.BasketChangeEvent
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.domain.basket.BasketRepository
import com.gkgio.domain.basket.BasketUseCase
import com.gkgio.domain.cookers.LoadFoodItemUseCase
import com.gkgio.domain.cookers.detail.CookerAddress
import com.gkgio.domain.location.Coordinates
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.math.BigDecimal
import javax.inject.Inject

class FoodItemViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val lunchUiTransformer: LunchUiTransformer,
    private val mealUiTransformer: MealUiTransformer,
    private val loadFoodItemUseCase: LoadFoodItemUseCase,
    private val basketUseCase: BasketUseCase,
    private val priceFormatter: PriceFormatter,
    private val basketRepository: BasketRepository,
    private val basketChangeEvent: BasketChangeEvent,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    lateinit var foodItemRequest: FoodItemRequest

    val state = MutableLiveData<State>()
    val showClearBasketWarning = SingleLiveEvent<Unit>()
    val showDialogDeleteFromBasket = SingleLiveEvent<Unit>()
    val closeDialog = SingleLiveEvent<Unit>()

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
                state.value = state.nonNullValue.copy(
                    lunchUi = it,
                    isLoading = false,
                    currentPricePure = it.pricePure,
                    currentPriceOneItem = it.pricePure,
                    imageUrl = it.imageUrl,
                    currentPriceFormatted = priceFormatter.format(it.pricePure),
                    nameFood = it.name
                )
                loadBasketItem()
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
                state.value =
                    state.nonNullValue.copy(
                        mealUi = it,
                        isLoading = false,
                        currentPricePure = it.purePrice,
                        currentPriceOneItem = it.purePrice,
                        imageUrl = it.imageUrl,
                        currentPriceFormatted = priceFormatter.format(it.purePrice),
                        nameFood = it.name
                    )
                loadBasketItem()
            }, {
                state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
                processThrowable(it)
            }).addDisposable()
    }

    private fun loadBasketItem() {
        basketUseCase
            .loadBasketItem(foodItemRequest.foodId)
            .applySchedulers()
            .subscribe({
                state.value =
                    state.nonNullValue.copy(
                        currentCount = it.count,
                        currentPricePure = it.price,
                        currentPriceOneItem = it.priceOneItem,
                        currentPriceFormatted = priceFormatter.format(it.price),
                        goodsWasInBasket = true
                    )
            }, {
                Timber.d(it)
            }).addDisposable()
    }

    fun onAddToBasketClick() {
        if (state.nonNullValue?.nameFood != null && state.nonNullValue?.currentPricePure != null
            && state.nonNullValue?.currentCount != null
        ) {
            if (state.nonNullValue.goodsWasInBasket) {
                updateBasketItemCount()
            } else {
                val basketCountAndSum = basketRepository.loadBasketCountAndSum()
                when {
                    basketCountAndSum == null -> {
                        addToBasket()
                    }
                    basketCountAndSum.cookerId != foodItemRequest.cookerId -> {
                        showClearBasketWarning.call()
                    }
                    else -> {
                        addToBasket()
                    }
                }

            }
        }
    }

    private fun addToBasket() {
        basketUseCase
            .addToBasket(
                foodItemRequest.foodId,
                state.nonNullValue.nameFood!!,
                state.nonNullValue?.imageUrl,
                state.nonNullValue.currentPricePure!!,
                state.nonNullValue.currentPricePure!!,
                foodItemRequest.cookerId,
                foodItemRequest.type,
                foodItemRequest.cookerAddressUi?.let {
                    CookerAddress(
                        it.street,
                        it.house,
                        it.flat,
                        it.floor,
                        Coordinates(
                            it.coordinates.latitude,
                            it.coordinates.longitude
                        )
                    )
                },
                state.nonNullValue.currentCount
            )
            .applySchedulers()
            .subscribe({
                state.value = state.nonNullValue.copy(goodsWasInBasket = true)
                basketChangeEvent.onComplete("")
            }, {
                Timber.d(it)
            }).addDisposable()
    }

    private fun updateBasketItemCount() {
        basketUseCase
            .updateBasketItemCount(
                foodItemRequest.foodId,
                state.nonNullValue.currentCount,
                state.nonNullValue.currentPricePure!!,
                foodItemRequest.cookerId,
                foodItemRequest.cookerAddressUi?.let {
                    CookerAddress(
                        it.street,
                        it.house,
                        it.flat,
                        it.floor,
                        Coordinates(
                            it.coordinates.latitude,
                            it.coordinates.longitude
                        )
                    )
                }
            )
            .applySchedulers()
            .subscribe({
                basketChangeEvent.onComplete("")
                closeDialog.call()
            }, {
                Timber.d(it)
            }).addDisposable()
    }

    fun onPlusCountClick() {
        val portions = state.nonNullValue.mealUi?.portions ?: state.nonNullValue.lunchUi?.portions
        if (portions != null && state.nonNullValue.currentCount + 1 <= portions) {
            val newPrice =
                state.nonNullValue.currentPricePure!! + state.nonNullValue.currentPriceOneItem!!
            state.value = state.nonNullValue.copy(
                currentCount = state.nonNullValue.currentCount + 1,
                currentPricePure = newPrice,
                currentPriceFormatted = priceFormatter.format(newPrice)
            )
        }
    }

    fun onMinusCountClick() {
        if (state.nonNullValue.currentCount > 1) {
            val newPrice =
                state.nonNullValue.currentPricePure!! - state.nonNullValue.currentPriceOneItem!!
            state.value = state.nonNullValue.copy(
                currentCount = state.nonNullValue.currentCount - 1,
                currentPricePure = newPrice,
                currentPriceFormatted = priceFormatter.format(newPrice)
            )
        } else if (state.nonNullValue.goodsWasInBasket) {
            showDialogDeleteFromBasket.call()
        }
    }

    fun onDeleteFromBasketClick() {
        basketUseCase
            .removeFromBasket(
                foodItemRequest.foodId,
                foodItemRequest.cookerId,
                foodItemRequest.cookerAddressUi?.let {
                    CookerAddress(
                        it.street,
                        it.house,
                        it.flat,
                        it.floor,
                        Coordinates(
                            it.coordinates.latitude,
                            it.coordinates.longitude
                        )
                    )
                })
            .applySchedulers()
            .subscribe({
                basketChangeEvent.onComplete("")
                closeDialog.call()
            }, {
                Timber.d(it)
            }).addDisposable()
    }

    fun onClearBasketClick() {
        basketUseCase
            .clearBasket()
            .applySchedulers()
            .subscribe({
                onPlusCountClick()
                onAddToBasketClick()
            }, {
                Timber.d(it)
            }).addDisposable()
    }

    data class State(
        val isLoading: Boolean,
        val isInitialError: Boolean = false,
        val lunchUi: LunchUi? = null,
        val mealUi: MealUi? = null,
        val nameFood: String? = null,
        val currentPriceFormatted: String? = null,
        val currentPricePure: BigDecimal? = null,
        val currentPriceOneItem: BigDecimal? = null,
        val imageUrl: String? = null,
        val currentCount: Int = 1,
        val goodsWasInBasket: Boolean = false
    )
}