package com.gkgio.borsch.cookers

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.cookers.detail.BasketCountAndSumUiTransformer
import com.gkgio.borsch.cookers.detail.CookerAddressUi
import com.gkgio.borsch.cookers.detail.CookerUi
import com.gkgio.borsch.cookers.detail.CookerUiTransformer
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.ext.isNonInitialized
import com.gkgio.borsch.ext.nonNullValue
import com.gkgio.borsch.navigation.Screens
import com.gkgio.borsch.utils.SingleLiveEvent
import com.gkgio.borsch.utils.events.AddressChangedEvent
import com.gkgio.domain.address.Address
import com.gkgio.domain.address.LoadAddressesUseCase
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.domain.cookers.Cooker
import com.gkgio.domain.cookers.CookersRequest
import com.gkgio.domain.cookers.CookersWithoutAuthRequest
import com.gkgio.domain.cookers.LoadCookersUseCase
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class CookersViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val loadCookersUseCase: LoadCookersUseCase,
    private val loadAddressesUseCase: LoadAddressesUseCase,
    private val cookerUiTransformer: CookerUiTransformer,
    private val cookerCountAndSumUiTransformer: BasketCountAndSumUiTransformer,
    addressChangedEvent: AddressChangedEvent,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()
    val openAddressesSheet = SingleLiveEvent<Unit>()

    init {
        if (state.isNonInitialized()) {
            state.value = State(false)

            loadAddressesUseCase
                .getLastSavedAddress()
                .applySchedulers()
                .subscribe({
                    state.value = state.nonNullValue.copy(
                        lastAddedAddress = String.format(
                            "%s, %s",
                            it.street,
                            it.house
                        )
                    )
                }, {
                    //empty
                }).addDisposable()


            loadCookers()

            addressChangedEvent
                .getEventResult()
                .applySchedulers()
                .subscribe {
                    state.value = state.nonNullValue.copy(
                        lastAddedAddress = it
                    )
                    loadCookers()
                }.addDisposable()
        }
    }

    fun loadCookers() {
        loadCookersUseCase
            .loadCookersList()
            .map { cookerList -> cookerList.map { cookerUiTransformer.transform(it) } }
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isLoading = true) }
            .subscribe({
                state.value =
                    state.nonNullValue.copy(isLoading = false, cookers = it, isInitialError = false)
            }, {
                state.value = state.nonNullValue.copy(isLoading = false, isInitialError = true)
                processThrowable(it)
            }).addDisposable()
    }

    fun onCookerFoodClick(
        cookerId: String,
        foodId: String,
        type: Int,
        cookerAddressUi: CookerAddressUi?
    ) {
        router.navigateTo(
            Screens.CookerDetailFragmentScreen(
                cookerId,
                foodId,
                type,
                cookerAddressUi
            )
        )
    }

    fun onCookerClick(cookerId: String) {
        router.navigateTo(Screens.CookerDetailFragmentScreen(cookerId))
    }

    fun onCurrentAddressContainerClick() {
        openAddressesSheet.call()
    }

    data class State(
        val isLoading: Boolean,
        val isInitialError: Boolean = false,
        val cookers: List<CookerUi>? = null,
        val lastAddedAddress: String? = null
    )
}