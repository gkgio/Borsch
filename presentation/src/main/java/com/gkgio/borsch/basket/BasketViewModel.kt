package com.gkgio.borsch.basket

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.cookers.CookersFragment.Companion.MEAL_TYPE
import com.gkgio.borsch.cookers.detail.BasketCountAndSumUi
import com.gkgio.borsch.cookers.detail.BasketCountAndSumUiTransformer
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.ext.isNonInitialized
import com.gkgio.borsch.ext.nonNullValue
import com.gkgio.borsch.navigation.Screens
import com.gkgio.borsch.utils.SingleLiveEvent
import com.gkgio.borsch.utils.events.BasketChangeEvent
import com.gkgio.borsch.utils.events.OrderChangeEvent
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.domain.auth.AuthRepository
import com.gkgio.domain.basket.BasketOrderRequest
import com.gkgio.domain.basket.BasketRepository
import com.gkgio.domain.basket.BasketUseCase
import com.gkgio.domain.cookers.detail.CookerAddress
import com.gkgio.domain.location.Coordinates
import ru.terrakok.cicerone.Router
import java.math.BigDecimal
import javax.inject.Inject

class BasketViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val basketUseCase: BasketUseCase,
    private val basketRepository: BasketRepository,
    private val basketDataUiTransformer: BasketDataUiTransformer,
    private val basketCountAndSumUiTransformer: BasketCountAndSumUiTransformer,
    private val basketChangeEvent: BasketChangeEvent,
    private val orderChangeEvent: OrderChangeEvent,
    private val authRepository: AuthRepository,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()

    init {
        if (state.isNonInitialized()) {
            state.value = State(false)

            loadBasketData()

            basketChangeEvent
                .getEventResult()
                .applySchedulers()
                .subscribe({
                    loadBasketData()
                }, {
                    //ignore
                }).addDisposable()
        }
    }

    fun onPlusBtnClick(basketDataUi: BasketDataUi, position: Int) {
        val newPrice =
            (basketDataUi.priceOneItem.multiply(basketDataUi.count.toBigDecimal()) + basketDataUi.priceOneItem)
        updateBasketCount(basketDataUi.id, basketDataUi.count + 1, newPrice)
    }

    fun onMinusBtnClick(basketDataUi: BasketDataUi, position: Int) {
        val newCount = basketDataUi.count - 1
        if (newCount == 0) {
            basketUseCase.removeFromBasket(
                basketDataUi.id,
                state.nonNullValue.basketCountAndSumUi!!.cookerId,
                state.nonNullValue.basketCountAndSumUi?.cookerAddressUi?.let {
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
                }).applySchedulers()
                .subscribe({
                    val newBasketList = state.nonNullValue.basketDataList?.toMutableList()
                    newBasketList?.remove(basketDataUi)
                    state.value = state.nonNullValue.copy(basketDataList = newBasketList)
                    basketChangeEvent.onComplete("")
                }, {

                }).addDisposable()
        } else {
            val newPrice =
                (basketDataUi.priceOneItem.multiply(basketDataUi.count.toBigDecimal()) - basketDataUi.priceOneItem)
            updateBasketCount(basketDataUi.id, newCount, newPrice)
        }
    }

    private fun updateBasketCount(id: String, count: Int, newPrice: BigDecimal) {
        basketUseCase.updateBasketItemCount(
            id,
            count,
            newPrice,
            state.nonNullValue.basketCountAndSumUi!!.cookerId,
            state.nonNullValue.basketCountAndSumUi?.cookerAddressUi?.let {
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
            .map { basketList -> basketList.map { basketDataUiTransformer.transform(it) } }
            .applySchedulers()
            .subscribe({
                state.value = state.nonNullValue.copy(basketDataList = it)
                basketChangeEvent.onComplete("")
            }, {
                //
            }).addDisposable()
    }

    private fun loadBasketData() {
        basketUseCase
            .loadBasketData()
            .map { basketList -> basketList.map { basketDataUiTransformer.transform(it) } }
            .applySchedulers()
            .subscribe({
                state.value = state.nonNullValue.copy(basketDataList = it)
                loadBasketCountAndSum()
            }, {
                //ignore
            }).addDisposable()
    }

    private fun loadBasketCountAndSum() {
        val basketCountAndSumUi =
            basketRepository.loadBasketCountAndSum()?.let { basketCountAndSumUi ->
                basketCountAndSumUiTransformer.transform(basketCountAndSumUi)
            }
        state.value = state.nonNullValue.copy(basketCountAndSumUi = basketCountAndSumUi)
    }

    fun onOrderConfirmBtnClick(isInsidePage: Boolean? = null) {
        if (authRepository.getAuthToken() != null) {
            state.nonNullValue.basketDataList?.let { basketDataList ->
                val mealsIds = mutableListOf<String>()
                val lunchesIds = mutableListOf<String>()
                basketDataList.forEach {
                    if (it.type == MEAL_TYPE) {
                        for (count in 0 until it.count) {
                            mealsIds.add(it.id)
                        }
                    } else {
                        for (count in 0 until it.count) {
                            lunchesIds.add(it.id)
                        }
                    }
                }
                basketUseCase
                    .createOrder(
                        BasketOrderRequest(
                            mealsIds,
                            lunchesIds
                        ),
                        state.nonNullValue.basketCountAndSumUi!!.cookerId
                    )
                    .applySchedulers()
                    .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
                    .subscribe({
                        orderChangeEvent.onComplete("")
                        state.value = state.nonNullValue.copy(
                            isLoading = false,
                            basketDataList = null,
                            basketCountAndSumUi = null
                        )
                        if (isInsidePage == true) {
                            router.backTo(Screens.MainFragmentScreen)
                            router.switchTo(R.id.tab_orders)
                        } else {
                            router.switchTo(R.id.tab_orders)
                        }
                    }, {
                        state.value = state.nonNullValue.copy(isLoading = false)
                        processThrowable(it)
                    }).addDisposable()
            }
        } else {
            router.navigateTo(Screens.SettingsFragmentScreen(true))
        }
    }

    fun onFindCookerButtonClick() {
        router.switchTo(R.id.tab_cookers)
    }

    fun onCookerAddressClick() {
        state.nonNullValue.basketCountAndSumUi?.cookerAddressUi?.let { cookerAddress ->
            router.navigateTo(
                Screens.RoutScreen(
                    Coordinates(
                        cookerAddress.coordinates.latitude,
                        cookerAddress.coordinates.longitude
                    )
                )
            )
        }
    }

    data class State(
        val isLoading: Boolean,
        val isInitialError: Boolean = false,
        val basketDataList: List<BasketDataUi>? = null,
        val basketCountAndSumUi: BasketCountAndSumUi? = null
    )
}