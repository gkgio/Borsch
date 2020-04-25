package com.gkgio.borsch.orders

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.ext.isNonInitialized
import com.gkgio.borsch.ext.nonNullValue
import com.gkgio.borsch.navigation.Screens
import com.gkgio.borsch.utils.SingleLiveEvent
import com.gkgio.borsch.utils.events.OrderChangeEvent
import com.gkgio.borsch.utils.events.UserProfileChanged
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.domain.auth.AuthRepository
import com.gkgio.domain.basket.BasketUseCase
import com.gkgio.domain.basket.OrderData
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class OrdersViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val basketUseCase: BasketUseCase,
    private val authRepository: AuthRepository,
    private val orderDataUiTransformer: OrderDataUiTransformer,
    orderChangeEvent: OrderChangeEvent,
    userProfileChanged: UserProfileChanged,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()
    val openOrderDetailSheet = SingleLiveEvent<String>()

    init {
        if (state.isNonInitialized()) {
            state.value = State(false)

            loadOrderData()

            orderChangeEvent
                .getEventResult()
                .applySchedulers()
                .subscribe({
                    loadOrderData()
                }, {
                    //
                }).addDisposable()

            userProfileChanged
                .getEventResult()
                .applySchedulers()
                .subscribe({
                    loadOrderData()
                }, {
                    //
                }).addDisposable()
        }
    }

    fun loadOrderData() {
        if (authRepository.getAuthToken() != null) {
            basketUseCase
                .getBasketOrder()
                .map { orderDataList -> orderDataList.map { orderDataUiTransformer.transform(it) } }
                .applySchedulers()
                .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
                .subscribe({
                    state.value = state.nonNullValue.copy(orderDataList = it, isLoading = false)
                }, {
                    state.value = state.nonNullValue.copy(isLoading = false)
                    processThrowable(it)
                }).addDisposable()
        } else {
            state.value = state.nonNullValue.copy(isLoading = false, orderDataList = null)
        }
    }

    fun onOpenChatClick(orderId: String) {
        val user = authRepository.loadUserProfile()
        if (user != null) {
            router.navigateTo(Screens.OrderChatFragmentScreen(orderId, user.id))
        }
    }

    fun onOrderDetailOpenClick(orderId: String) {
        openOrderDetailSheet.value = orderId
    }

    data class State(
        val isLoading: Boolean,
        val isInitialError: Boolean = false,
        val orderDataList: List<OrderDataUi>? = null
    )
}