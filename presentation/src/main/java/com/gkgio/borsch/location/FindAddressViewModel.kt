package com.gkgio.borsch.location

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.ext.nonNullValue
import com.gkgio.borsch.navigation.Screens
import com.gkgio.borsch.utils.SingleLiveEvent
import com.gkgio.borsch.utils.events.AddressChangedEvent
import com.gkgio.domain.address.*
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.domain.location.Coordinates
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class FindAddressViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository,
    private val loadAddressesUseCase: LoadAddressesUseCase,
    private val addressChangedEvent: AddressChangedEvent,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()
    val closeKeyBoard = SingleLiveEvent<Unit>()

    init {
        state.value = State(false)
    }

    fun onSearchTextChanged(searchText: String) {
        loadAddressesUseCase
            .loadGeoSuggestions(
                GeoSuggestionsRequest(
                    query = searchText,
                    count = 8
                )
            )
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isProgress = true) }
            .subscribe({
                state.value =
                    state.nonNullValue.copy(isProgress = false, geoSuggestionList = it.suggestions)
            }, {
                state.value = state.nonNullValue.copy(isProgress = false)
                closeKeyBoard.call()
                processThrowable(it)
            }).addDisposable()
    }

    fun onAddressSelectClick(geoSuggestion: GeoSuggestion, isOpenFromOnboarding: Boolean) {
        loadAddressesUseCase
            .loadGeoSuggestions(
                GeoSuggestionsRequest(
                    query = geoSuggestion.value
                )
            ).flatMapCompletable { geoSuggestionList ->
                loadAddressesUseCase
                    .addNewClientAddress(
                        AddressAddingRequest(
                            geoSuggestionList.suggestions[0].data.city,
                            geoSuggestionList.suggestions[0].data.country,
                            null,
                            geoSuggestionList.suggestions[0].data.house,
                            Coordinates(
                                geoSuggestionList.suggestions[0].data.geo_lat!!.toDouble(),
                                geoSuggestionList.suggestions[0].data.geo_lon!!.toDouble()
                            ),
                            geoSuggestionList.suggestions[0].data.streetWithType,
                            geoSuggestionList.suggestions[0].data.block
                        )
                    )
            }
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isProgress = true) }
            .subscribe({
                state.value = state.nonNullValue.copy(isProgress = false)
                addressChangedEvent.onComplete(
                    String.format(
                        "%s, %s%s",
                        geoSuggestion.data.streetWithType,
                        geoSuggestion.data.house,
                        if (geoSuggestion.data.block != null) ", ${geoSuggestion.data.block}" else ""
                    )
                )
                if (isOpenFromOnboarding) {
                    router.newRootScreen(Screens.MainFragmentScreen)
                } else {
                    router.backTo(Screens.MainFragmentScreen)
                }
            }, { throwable ->
                state.value = state.nonNullValue.copy(isProgress = false)
                processThrowable(throwable)
            }).addDisposable()

    }

    data class State(
        val isProgress: Boolean,
        val geoSuggestionList: List<GeoSuggestion>? = null
    )
}