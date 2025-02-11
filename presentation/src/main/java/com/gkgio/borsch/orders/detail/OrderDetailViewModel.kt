package com.gkgio.borsch.orders.detail

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.ext.isNonInitialized
import com.gkgio.borsch.ext.nonNullValue
import com.gkgio.borsch.navigation.Screens
import com.gkgio.borsch.utils.SingleLiveEvent
import com.gkgio.borsch.utils.events.OrderChangeEvent
import com.gkgio.domain.auth.AuthRepository
import com.gkgio.domain.basket.BasketUseCase
import com.gkgio.domain.location.Coordinates
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class OrderDetailViewModel @Inject constructor(
    private val router: Router,
    private val basketUseCase: BasketUseCase,
    private val orderDetailUiTransformer: OrderDetailUiTransformer,
    private val authRepository: AuthRepository,
    private val orderChangeEvent: OrderChangeEvent,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()
    val showOrderDeleteDialog = SingleLiveEvent<Unit>()
    val closeFragmentEvent = SingleLiveEvent<Unit>()

    lateinit var orderId: String

    fun init(orderId: String) {
        if (state.isNonInitialized()) {
            this.orderId = orderId

            state.value = State(false)

            loadOrderDetailData(orderId)
        }
    }

    private fun loadOrderDetailData(orderId: String) {
        basketUseCase
            .getBasketOrderDetail(orderId)
            .map { orderDetailUiTransformer.transform(it) }
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
        state.nonNullValue.orderDetailDataUi?.cookerAddress?.let { cookerAddress ->
            router.navigateTo(
                Screens.RoutScreen(
                    Coordinates(
                        cookerAddress.location.latitude,
                        cookerAddress.location.longitude
                    )
                )
            )
        }
    }

    fun onOpenChatClick() {
        val user = authRepository.loadUserProfile()
        if (user != null && state.nonNullValue.orderDetailDataUi?.orderId != null) {
            router.navigateTo(
                Screens.OrderChatFragmentScreen(
                    state.nonNullValue.orderDetailDataUi?.orderId!!,
                    user.id
                )
            )
        }
    }

    fun onDeleteOrderClick() {
        showOrderDeleteDialog.call()
    }

    fun onCancelOrderConfirm() {
        basketUseCase
            .cancelOrder(orderId)
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
            .subscribe({
                state.value = state.nonNullValue.copy(isLoading = false)
                orderChangeEvent.onComplete("")
                closeFragmentEvent.call()
            }, {
                state.value = state.nonNullValue.copy(isLoading = false)
                processThrowable(it)
            }).addDisposable()
    }

    data class State(
        val isLoading: Boolean,
        val isInitialError: Boolean = false,
        val orderDetailDataUi: OrderDetailUi? = null
    )
}