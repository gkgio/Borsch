package com.gkgio.borsch.location

import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.ext.isNonInitialized
import com.gkgio.borsch.ext.nonNullValue
import com.gkgio.borsch.navigation.Screens
import com.gkgio.borsch.utils.SingleLiveEvent
import com.gkgio.borsch.utils.events.AddressChangedEvent
import com.gkgio.domain.address.*
import com.gkgio.domain.location.Coordinates
import com.gkgio.domain.location.LoadLocationUseCase
import com.google.android.gms.maps.model.LatLng
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val router: Router,
    private val locationUseCase: LoadLocationUseCase,
    private val loadAddressesUseCase: LoadAddressesUseCase,
    private val addressChangedEvent: AddressChangedEvent,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    companion object {
        private const val LOCATION_CHECKING_TIMEOUT = 3500L
    }

    val state = MutableLiveData<State>()
    val checkLocationPermission = SingleLiveEvent<Unit>()
    val moveCameraToPosition = SingleLiveEvent<LatLng>()
    var lastRequestLocation: Location? = null
    val openGpsGrantedDialog = SingleLiveEvent<Unit>()

    init {
        if (state.isNonInitialized()) {
            state.value = State()
        }
    }

    fun onMapLoaded() {
        loadAddressesUseCase
            .getSavedAddresses()
            .applySchedulers()
            .subscribe({
                val latLng = LatLng(
                    it[0].location.latitude,
                    it[0].location.longitude
                )
                moveCameraToPosition.value = latLng
                onMapPositionChanged(latLng)
            }, {
                checkLocationPermission.call()
            }).addDisposable()
    }

    fun onMapPositionChanged(latLng: LatLng, zoom: Float = 20f) {
        val needUpdate: Boolean
        val newLocation = Location("").apply {
            latitude = latLng.latitude
            longitude = latLng.longitude
        }
        if (lastRequestLocation != null) {
            val metersCheck =
                when {
                    zoom < 5 -> 10000f
                    zoom >= 5 && zoom < 8 -> 1000f
                    zoom >= 8 && zoom < 11 -> 200f
                    zoom >= 11 && zoom < 13 -> 50f
                    zoom >= 13 && zoom < 16 -> 20f
                    else -> 10f
                }
            needUpdate = lastRequestLocation!!.distanceTo(newLocation) > metersCheck
            if (needUpdate) {
                lastRequestLocation = newLocation
            }
        } else {
            needUpdate = true
            lastRequestLocation = newLocation
        }
        if (needUpdate) {
            loadAddressesUseCase
                .loadGeoSuggestions(
                    GeoSuggestionsRequest(
                        latLng.latitude,
                        latLng.longitude
                    )
                )
                .applySchedulers()
                .doOnSubscribe { state.value = state.nonNullValue.copy(isProgress = true) }
                .subscribe({
                    state.value =
                        state.nonNullValue.copy(
                            isProgress = false,
                            geoSuggestionData = if (it.suggestions.isNotEmpty()) it.suggestions[0] else null
                        )
                }, { throwable ->
                    state.value = state.nonNullValue.copy(isProgress = false)
                    processThrowable(throwable)
                }).addDisposable()
        }
    }

    fun onConfirmAddressClick(isOpenFromOnboarding: Boolean) {
        state.nonNullValue.geoSuggestionData?.data?.let {
            if (it.geo_lat != null && it.geo_lon != null) {
                loadAddressesUseCase
                    .addNewClientAddress(
                        AddressAddingRequest(
                            it.city,
                            it.country,
                            null,
                            it.house,
                            Coordinates(
                                it.geo_lat!!.toDouble(),
                                it.geo_lon!!.toDouble()
                            ),
                            it.streetWithType,
                            it.block
                        )
                    )
                    .applySchedulers()
                    .doOnSubscribe {
                        state.value = state.nonNullValue.copy(isProgressCircle = true)
                    }
                    .subscribe({
                        state.value = state.nonNullValue.copy(isProgressCircle = false)
                        addressChangedEvent.onComplete(
                            String.format(
                                "%s, %s%s",
                                it.streetWithType,
                                it.house,
                                if (it.block != null) ", ${it.block}" else ""
                            )
                        )
                        if (isOpenFromOnboarding) {
                            router.newRootScreen(Screens.MainFragmentScreen)
                        } else {
                            onBackClick()
                        }
                    }, { throwable ->
                        state.value = state.nonNullValue.copy(isProgressCircle = false)
                        processThrowable(throwable)
                    }).addDisposable()
            }
        }
    }

    fun onLocationPermissionResultLoaded(result: Boolean, isOpenFromOnboarding: Boolean) =
        if (result) {
            onLocationPermissionGranted()
        } else {
            onLocationPermissionDenied(isOpenFromOnboarding)
        }

    private fun onLocationPermissionGranted() {
        locationUseCase.getLocationOrThrow()
            .applySchedulers()
            .subscribe({
                val latLng = LatLng(
                    it.latitude,
                    it.longitude
                )
                moveCameraToPosition.value = latLng
                onMapPositionChanged(latLng)
            }, {
                Timber.e(it, "Error get coordinates")
                openGpsGrantedDialog.call()
            }).addDisposable()
    }

    private fun onLocationPermissionDenied(isOpenFromOnboarding: Boolean) {
        router.navigateTo(Screens.FindAddressFragmentScreen(isOpenFromOnboarding))
    }

    fun onGpsNotGranted(isOpenFromOnboarding: Boolean) {
        router.navigateTo(Screens.FindAddressFragmentScreen(isOpenFromOnboarding))
    }

    fun onChangeAddressClick(isOpenFromOnboarding: Boolean) {
        router.navigateTo(Screens.FindAddressFragmentScreen(isOpenFromOnboarding))
    }

    fun onGpsGranted() {
        locationUseCase.startUpdateLocationListener()
            .timeout(LOCATION_CHECKING_TIMEOUT, TimeUnit.MILLISECONDS)
            .applySchedulers()
            .doOnSubscribe { state.value = state.nonNullValue.copy(isProgressCircle = true) }
            .subscribe(
                {
                    state.value = state.nonNullValue.copy(isProgressCircle = false)
                    val latLng = LatLng(
                        it.latitude,
                        it.longitude
                    )
                    moveCameraToPosition.value = latLng
                    onMapPositionChanged(latLng)
                },
                {
                    state.value =
                        state.nonNullValue.copy(isProgressCircle = false)
                    processThrowable(it, "Error get coordinates")
                }
            )
            .addDisposable()
    }

    data class State(
        val isProgress: Boolean = false,
        val isProgressCircle: Boolean = false,
        val geoSuggestionData: GeoSuggestion? = null
    )

}