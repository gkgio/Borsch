package com.gkgio.borsch.location

import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.domain.address.LoadAddressesUseCase
import com.gkgio.domain.location.Coordinates
import com.gkgio.domain.location.LoadLocationUseCase
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val router: Router,
    private val locationUseCase: LoadLocationUseCase,
    private val loadCityUseCase: LoadAddressesUseCase,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    var lastKnownCoordinates: Coordinates? = null

    fun onMapLoaded() {

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
                lastKnownCoordinates = it
            }, {
                Timber.e(it, "Error get coordinates")
                onLocationPermissionDenied()
            }).addDisposable()
    }

    private fun onLocationPermissionDenied() {

    }

}