package com.gkgio.borsch.location.saved

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.ext.nonNullValue
import com.gkgio.borsch.navigation.Screens
import com.gkgio.borsch.utils.SingleLiveEvent
import com.gkgio.borsch.utils.events.AddressChangedEvent
import com.gkgio.domain.address.Address
import com.gkgio.domain.address.AddressAddingRequest
import com.gkgio.domain.address.LoadAddressesUseCase
import com.gkgio.domain.analytics.AnalyticsRepository
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SavedAddressesViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val addressUiTransformer: AddressUiTransformer,
    private val addressesUseCase: LoadAddressesUseCase,
    private val addressChangedEvent: AddressChangedEvent,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()
    val dialogDismiss = SingleLiveEvent<Unit>()

    init {
        state.value = State(false)

        addressesUseCase
            .getSavedAddresses()
            .applySchedulers()
            .map { listAddresses -> listAddresses.map { addressUiTransformer.transform(it) } }
            .subscribe({
                it[0].isSelected = true
                state.value = state.nonNullValue.copy(addressesList = it)
            }, {
                processThrowable(it)
            }).addDisposable()
    }

    fun onAddressClick(addressUi: AddressUi) {
        val addressesList = state.nonNullValue.addressesList

        addressesList?.forEach {
            it.isSelected = it == addressUi
        }
        state.value = state.nonNullValue.copy(
            addressesList = addressesList,
            currentSelectedAddress = addressUi
        )
    }

    fun onConfirmBtnClick() {
        if (state.nonNullValue.addressesList != null && state.nonNullValue.currentSelectedAddress != null) {
            addressesUseCase
                .addNewClientAddress(
                    AddressAddingRequest(
                        state.nonNullValue.currentSelectedAddress!!.address.city,
                        state.nonNullValue.currentSelectedAddress!!.address.country,
                        state.nonNullValue.currentSelectedAddress!!.address.flat,
                        state.nonNullValue.currentSelectedAddress!!.address.house,
                        state.nonNullValue.currentSelectedAddress!!.address.location,
                        state.nonNullValue.currentSelectedAddress!!.address.street,
                        state.nonNullValue.currentSelectedAddress!!.address.block
                    )
                )
                .applySchedulers()
                .doOnSubscribe { state.value = state.nonNullValue.copy(isProgress = true) }
                .subscribe({
                    state.value = state.nonNullValue.copy(isProgress = false)
                    with(state.nonNullValue.currentSelectedAddress!!.address) {
                        addressChangedEvent.onComplete(
                            String.format(
                                "%s, %s%s",
                                street,
                                house,
                                if (block != null) ", $block" else ""
                            )
                        )
                    }
                    dialogDismiss.call()
                }, { throwable ->
                    state.value = state.nonNullValue.copy(isProgress = false)
                    processThrowable(throwable)
                }).addDisposable()
        } else {
            dialogDismiss.call()
        }

    }

    fun onAddNewAddressClick() {
        router.navigateTo(Screens.LocationFragmentScreen())
        dialogDismiss.call()
    }

    data class State(
        val isProgress: Boolean,
        val addressesList: List<AddressUi>? = null,
        val currentSelectedAddress: AddressUi? = null
    )
}