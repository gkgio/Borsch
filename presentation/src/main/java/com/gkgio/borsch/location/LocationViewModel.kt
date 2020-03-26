package com.gkgio.borsch.location

import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.ext.isNonInitialized
import com.gkgio.borsch.ext.nonNullValue
import com.gkgio.borsch.utils.SingleLiveEvent
import com.gkgio.domain.address.*
import com.gkgio.domain.location.Coordinates
import com.gkgio.domain.location.LoadLocationUseCase
import com.google.android.gms.maps.model.LatLng
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val router: Router,
    private val locationUseCase: LoadLocationUseCase,
    private val loadAddressesUseCase: LoadAddressesUseCase,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()
    val checkLocationPermission = SingleLiveEvent<Unit>()
    val moveCameraToPosition = SingleLiveEvent<LatLng>()
    var lastRequestLocation: Location? = null

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
                    zoom < 5 -> 5000f
                    zoom >= 5 && zoom < 8 -> 500f
                    zoom >= 8 && zoom < 11 -> 100f
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
                .subscribe({
                    state.value =
                        state.nonNullValue.copy(
                            isProgress = false,
                            geoSuggestionData = if (it.suggestions.isNotEmpty()) it.suggestions[0] else null
                        )
                }, {
                    Timber.d(it)
                }).addDisposable()
        }
    }

    fun onLocationPermissionResultLoaded(result: Boolean) =
        if (result) {
            onLocationPermissionGranted()
        } else {
            onLocationPermissionDenied()
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
            }).addDisposable()
    }

    private fun onLocationPermissionDenied() {

    }

    data class State(
        val isProgress: Boolean = false,
        val geoSuggestionData: GeoSuggestion? = null
    )

}