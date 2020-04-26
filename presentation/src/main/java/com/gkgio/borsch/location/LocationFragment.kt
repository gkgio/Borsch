package com.gkgio.borsch.location

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.*
import com.gkgio.borsch.utils.FragmentArgumentDelegate
import com.gkgio.borsch.utils.GpsUtils
import com.gkgio.borsch.utils.GpsUtils.Companion.GPS_REQUEST
import com.google.android.gms.maps.*
import kotlinx.android.synthetic.main.fragment_location.*

class LocationFragment : BaseFragment<LocationViewModel>(), OnMapReadyCallback {

    companion object {
        const val DEFAULT_MAP_ZOOM = 18.0f

        fun newInstance(isOpenFromOnboarding: Boolean = false) = LocationFragment().apply {
            this.isOpenFromOnboarding = isOpenFromOnboarding
        }
    }

    private var isOpenFromOnboarding: Boolean by FragmentArgumentDelegate()

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
            progressCircle.isVisible = state.isProgressCircle

            currentAddressTv.text = if (state.isProgress) getString(R.string.map_search_progress)
            else state.geoSuggestionData?.value

            if (state.geoSuggestionData == null) {
                btnConfirm.text = getString(R.string.map_confirm_not_found_btn)
                btnConfirm.setDebounceOnClickListener {
                    viewModel.onChangeAddressClick(isOpenFromOnboarding)
                }
            } else {
                btnConfirm.text = getString(R.string.map_confirm_btn)
                btnConfirm.setDebounceOnClickListener {
                    viewModel.onConfirmAddressClick(isOpenFromOnboarding)
                }
            }
        }

        viewModel.openGpsGrantedDialog.observeValue(this) {
            GpsUtils(requireContext()).turnGPSOn(object : GpsUtils.OnGpsListener {
                override fun gpsStatus(isGPSEnable: Boolean) {
                    if (isGPSEnable) {
                        viewModel.onGpsGranted()
                    } else {
                        viewModel.onGpsNotGranted(isOpenFromOnboarding)
                    }
                }

                override fun gpsSettingError() {
                    viewModel.onGpsNotGranted(isOpenFromOnboarding)
                }
            })
        }

        gpsBtnContainer.setDebounceOnClickListener {
            checkLocationPermission()
        }

        locationChangeContainer.setDebounceOnClickListener {
            viewModel.onChangeAddressClick(isOpenFromOnboarding)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_REQUEST) {
                viewModel.onGpsGranted()
            }
        }
    }

    private fun checkLocationPermission() {
        requestLocationPermission()
            .subscribe {
                viewModel.onLocationPermissionResultLoaded(it, isOpenFromOnboarding)
            }.addDisposable()
    }

}