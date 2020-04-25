package com.gkgio.borsch.orders.detail

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.ext.isNonInitialized
import com.gkgio.borsch.ext.nonNullValue
import com.gkgio.borsch.navigation.Screens
import com.gkgio.domain.auth.AuthRepository
import com.gkgio.domain.basket.BasketUseCase
import com.gkgio.domain.basket.OrderDetailData
import com.gkgio.domain.location.Coordinates
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class OrderDetailViewModel @Inject constructor(
    private val router: Router,
    private val basketUseCase: BasketUseCase,
    private val orderDetailDataUiTransformer: OrderDetailDataUiTransformer,
    private val authRepository: AuthRepository,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()

    fun init(orderId: String) {
        if (state.isNonInitialized()) {
            state.value = State(false)

            loadOrderDetailData(orderId)
        }
    }

    fun loadOrderDetailData(orderId: String) {
        basketUseCase
            .getBasketOrderDetail(orderId)
            .map { orderDetailDataUiTransformer.transform(it) }
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
            .subscribe({
                state.value = state.nonNullValue.copy(
                    isLoading = false,
                    isInitialError = false,
                    orderDetailDataUi = it
                )
            }, {
                state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
                processThrowable(it)
            }).addDisposable()
    }

    fun onCookerAddressClick() {
        state.nonNullValue.orderDetailDataUi?.cooker?.cookerAddress?.let { cookerAddress ->
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

    fun onOpenChatClick() {
        val user = authRepository.loadUserProfile()
        if (user != null && state.nonNullValue.orderDetailDataUi?.order?.orderId != null) {
            router.navigateTo(
                Screens.OrderChatFragmentScreen(
                    state.nonNullValue.orderDetailDataUi?.order?.orderId!!,
                    user.id
                )
            )
        }
    }

    data class State(
        val isLoading: Boolean,
        val isInitialError: Boolean = false,
        val orderDetailDataUi: OrderDetailDataUi? = null
    )
}