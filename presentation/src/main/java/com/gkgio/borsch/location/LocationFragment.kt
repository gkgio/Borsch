package com.gkgio.borsch.location

import android.os.Bundle
import android.view.View
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.*
import com.google.android.gms.maps.*
import kotlinx.android.synthetic.main.fragment_location.*

class LocationFragment : BaseFragment<LocationViewModel>(), OnMapReadyCallback {

    companion object {
        const val DEFAULT_MAP_ZOOM = 18.0f
    }

    private var googleMap: GoogleMap? = null

    override fun getLayoutId(): Int = R.layout.fragment_location

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.locationViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (childFragmentManager.findFragmentById(R.id.mapFullScreen) as SupportMapFragment)
            .getMapAsync(this)

        viewModel.checkLocationPermission.observeValue(this) {
            checkLocationPermission()
        }

        viewModel.moveCameraToPosition.observeValue(this) {
            googleMap?.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    it,
                    DEFAULT_MAP_ZOOM
                )
            )
        }

        viewModel.state.observeValue(this) { state ->
            currentAddressTv.text = state.geoSuggestionData?.value

            btnConfirm.text =
                if (state.geoSuggestionData == null) getString(R.string.map_confirm_not_found_btn)
                else getString(R.string.map_confirm_btn)
        }

        gpsBtnContainer.setDebounceOnClickListener {
            checkLocationPermission()
        }

        leftIconContainer.setDebounceOnClickListener {
            viewModel.onBackClick()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) = with(googleMap) {
        MapsInitializer.initialize(requireContext())

        setOnMapLoadedCallback { viewModel.onMapLoaded() }
        mapType = GoogleMap.MAP_TYPE_NORMAL
        isBuildingsEnabled = false

        setOnCameraMoveListener {
            viewModel.onMapPositionChanged(cameraPosition.target, cameraPosition.zoom)
        }

        uiSettings.apply {
            isMyLocationButtonEnabled = false
            isRotateGesturesEnabled = true
            isCompassEnabled = false
        }

        this@LocationFragment.googleMap = this
    }

    private fun checkLocationPermission() {
        requestLocationPermission()
            .subscribe {
                viewModel.onLocationPermissionResultLoaded(it)
            }.addDisposable()
    }

}