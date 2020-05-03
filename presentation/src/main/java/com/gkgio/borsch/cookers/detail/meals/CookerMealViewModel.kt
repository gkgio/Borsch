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
    private var savedFoodId: String? = null
    private var savedPrice: BigDecimal? = null
    private var savedName: String? = null
    private var savedType: Int? = null
    private var savedImageUrl: String? = null

    fun addToBasketClick(
        id: String,
        name: String,
        price: BigDecimal,
        imageUrl: String?,
        cookerId: String,
        cookerAddressUi: CookerAddressUi?,
        portions: Int,
        type: Int
    ) {
        val basketCountAndSum = basketRepository.loadBasketCountAndSum()
        when {
            basketCountAndSum == null -> {
                addToBasket(id, name, price, imageUrl, cookerId, cookerAddressUi, type)
            }
            basketCountAndSum.cookerId != cookerId -> {
                savedFoodId = id
                savedName = name
                savedPrice = price
                savedType = type
                savedImageUrl = imageUrl
                showClearBasketWarning.call()
            }
            else -> {
                checkBasket(id, name, price, imageUrl, cookerId, cookerAddressUi, portions, type)
            }
        }
    }

    fun addToBasketAfterCleaning(cookerId: String, cookerAddressUi: CookerAddressUi?) {
        if (savedFoodId != null && savedName != null && savedPrice != null && savedType != null) {
            basketUseCase
                .clearBasket()
                .applySchedulers()
                .subscribe({
                    addToBasket(
                        savedFoodId!!,
                        savedName!!,
                        savedPrice!!,
                        savedImageUrl,
                        cookerId,
                        cookerAddressUi,
                        savedType!!
                    )
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
        imageUrl: String?,
        cookerId: String,
        cookerAddressUi: CookerAddressUi?,
        portions: Int,
        type: Int
    ) {
        basketUseCase
            .loadBasketItem(foodId)
            .applySchedulers()
            .subscribe({
                if (it.count + 1 <= portions) {
                    updateItemCount(foodId, price, cookerId, cookerAddressUi)
                }
            }, {
                addToBasket(foodId, name, price, imageUrl, cookerId, cookerAddressUi, type)
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
        imageUrl: String?,
        cookerId: String,
        cookerAddressUi: CookerAddressUi?,
        type: Int
    ) {
        basketUseCase
            .addToBasket(
                foodId,
                name,
                imageUrl,
                price,
                price,
                cookerId,
                type,
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