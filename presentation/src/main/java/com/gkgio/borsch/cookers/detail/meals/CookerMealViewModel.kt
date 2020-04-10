package com.gkgio.borsch.cookers.detail.meals

import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.cookers.detail.CookerAddressUi
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.utils.SingleLiveEvent
import com.gkgio.borsch.utils.events.BasketChangeEvent
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.domain.basket.BasketRepository
import com.gkgio.domain.basket.BasketUseCase
import com.gkgio.domain.cookers.detail.CookerAddress
import com.gkgio.domain.location.Coordinates
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

    fun addToBasketClick(
        id: String,
        name: String,
        price: BigDecimal,
        cookerId: String,
        cookerAddressUi: CookerAddressUi?
    ) {
        val basketCountAndSum = basketRepository.loadBasketCountAndSum()
        when {
            basketCountAndSum == null -> {
                addToBasket(id, name, price, cookerId, cookerAddressUi)
            }
            basketCountAndSum.cookerId != cookerId -> {
                savedFoodId = id
                savedName = name
                savedPrice = price
                showClearBasketWarning.call()
            }
            else -> {
                checkBasket(id, name, price, cookerId, cookerAddressUi)
            }
        }
    }

    fun addToBasketAfterCleaning(cookerId: String, cookerAddressUi: CookerAddressUi?) {
        if (savedFoodId != null && savedName != null && savedPrice != null) {
            basketUseCase
                .clearBasket()
                .applySchedulers()
                .subscribe({
                    addToBasket(savedFoodId!!, savedName!!, savedPrice!!, cookerId, cookerAddressUi)
                    savedFoodId = null
                    savedName = null
                    savedPrice = null
                }, {
                    Timber.e(it)
                }).addDisposable()
        }
    }

    private fun checkBasket(
        foodId: String,
        name: String,
        price: BigDecimal,
        cookerId: String,
        cookerAddressUi: CookerAddressUi?
    ) {
        basketUseCase
            .loadBasketItem(foodId)
            .applySchedulers()
            .subscribe({
                updateItemCount(foodId, price, cookerId, cookerAddressUi)
            }, {
                addToBasket(foodId, name, price, cookerId, cookerAddressUi)
            }).addDisposable()
    }

    private fun updateItemCount(
        foodId: String,
        price: BigDecimal,
        cookerId: String,
        cookerAddressUi: CookerAddressUi?
    ) {
        basketUseCase.updateBasketItemCount(
            foodId,
            1,
            price,
            cookerId,
            cookerAddressUi?.let {
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
            true
        ).applySchedulers()
            .subscribe({
                basketChangeEvent.onComplete("")
            }, {
                Timber.e(it)
            }).addDisposable()
    }

    private fun addToBasket(
        foodId: String,
        name: String,
        price: BigDecimal,
        cookerId: String,
        cookerAddressUi: CookerAddressUi?
    ) {
        basketUseCase
            .addToBasket(
                foodId,
                name,
                price,
                price,
                cookerId,
                cookerAddressUi?.let {
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