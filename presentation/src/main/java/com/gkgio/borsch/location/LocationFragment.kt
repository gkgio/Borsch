package com.gkgio.borsch.location

import android.os.Bundle
import android.view.View
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.createViewModel
import com.gkgio.borsch.ext.requestLocationPermission
import com.gkgio.borsch.profile.about.AboutUsViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.fragment_about_us.*

class LocationFragment : BaseFragment<LocationViewModel>(), OnMapReadyCallback {

    companion object {
        const val DEFAULT_MAP_ZOOM = 13.0f
    }

    private var googleMap: GoogleMap? = null

    override fun getLayoutId(): Int = R.layout.fragment_about_us

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.locationViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        checkLocationPermission()

        (childFragmentManager.findFragmentById(R.id.mapFullScreen) as SupportMapFragment)
            .getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) = with(googleMap) {
        MapsInitializer.initialize(requireContext())

        setOnMapClickListener {

        }

        setOnMapLoadedCallback { viewModel.onMapLoaded() }
        mapType = GoogleMap.MAP_TYPE_NORMAL
        isBuildingsEnabled = false

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