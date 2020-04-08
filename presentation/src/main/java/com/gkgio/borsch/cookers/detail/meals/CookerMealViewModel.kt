package com.gkgio.borsch.cookers.detail.meals

import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.utils.SingleLiveEvent
import com.gkgio.borsch.utils.events.BasketChangeEvent
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.domain.basket.BasketRepository
import com.gkgio.domain.basket.BasketUseCase
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.math.BigDecimal
import javax.inject.Inject

class CookerMealViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val basketUseCase: BasketUseCase,
    private val basketRepository: BasketRepository,
    private val basketChangeEvent: BasketChangeEvent,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val showClearBasketWarning = SingleLiveEvent<Unit>()
    var savedFoodId: String? = null
    var savedPrice: BigDecimal? = null
    var savedName: String? = null

    fun addToBasketClick(id: String, name: String, price: BigDecimal, cookerId: String) {
        val basketCountAndSum = basketRepository.loadBasketCountAndSum()
        when {
            basketCountAndSum == null -> {
                addToBasket(id, name, price, cookerId)
            }
            basketCountAndSum.cookerId != cookerId -> {
                savedFoodId = id
                savedName = name
                savedPrice = price
                showClearBasketWarning.call()
            }
            else -> {
                checkBasket(id, name, price, cookerId)
            }
        }
    }

    fun addToBasketAfterCleaning(cookerId: String) {
        if (savedFoodId != null && savedName != null && savedPrice != null) {
            basketUseCase
                .clearBasket()
                .applySchedulers()
                .subscribe({
                    addToBasket(savedFoodId!!, savedName!!, savedPrice!!, cookerId)
                    savedFoodId = null
                    savedName = null
                    savedPrice = null
                }, {
                    Timber.e(it)
                }).addDisposable()
        }
    }

    private fun checkBasket(foodId: String, name: String, price: BigDecimal, cookerId: String) {
        basketUseCase
            .loadBasketItem(foodId)
            .applySchedulers()
            .subscribe({
                updateItemCount(foodId, price, cookerId)
            }, {
                addToBasket(foodId, name, price, cookerId)
            }).addDisposable()
    }

    private fun updateItemCount(foodId: String, price: BigDecimal, cookerId: String) {
        basketUseCase.updateBasketItemCount(
            foodId,
            1,
            price,
            cookerId,
            true
        ).applySchedulers()
            .subscribe({
                basketChangeEvent.onComplete("")
            }, {
                Timber.e(it)
            }).addDisposable()
    }

    private fun addToBasket(foodId: String, name: String, price: BigDecimal, cookerId: String) {
        basketUseCase
            .addToBasket(
                foodId,
                name,
                price,
                cookerId,
                1
            )
            .applySchedulers()
            .subscribe({
                basketChangeEvent.onComplete("")
            }, {
                Timber.d(it)
            }).addDisposable()
    }
}