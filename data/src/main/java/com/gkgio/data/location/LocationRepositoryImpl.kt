package com.gkgio.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.os.Looper
import com.gkgio.domain.location.Coordinates
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject
import com.gkgio.domain.location.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(val context: Context) : LocationRepository {

    companion object {
        private const val UPDATE_CHECK_LOCATION_INTERVAL = 20L

        val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = UPDATE_CHECK_LOCATION_INTERVAL
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private val subject = SingleSubject.create<Coordinates>()

    override fun getLastKnownLocationOrThrow(): Single<Coordinates> {
        return Single.create { emitter ->
            val lastLocationPosition: (Throwable) -> Unit = { error ->
                getLastKnownLocation()?.let { emitter.onSuccess(it) } ?: emitter.onError(error)
            }
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                try {
                    emitter.onSuccess(
                        Coordinates(
                            location.latitude,
                            location.longitude
                        )
                    )
                } catch (exception: Exception) {
                    lastLocationPosition(exception)
                }
            }

            fusedLocationClient.lastLocation.addOnFailureListener {
                lastLocationPosition(it)
            }
        }
    }

    override fun getLastKnownLocationIfPossible(): Maybe<Coordinates> {
        return Maybe.create { emitter ->
            val lastLocationPosition: () -> Unit = {
                getLastKnownLocation()?.let { emitter.onSuccess(it) } ?: emitter.onComplete()
            }
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    emitter.onSuccess(
                        Coordinates(
                            it.latitude,
                            it.longitude
                        )
                    )
                }
                lastLocationPosition()
            }

            fusedLocationClient.lastLocation.addOnFailureListener {
                lastLocationPosition()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation(): Coordinates? {
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val location = getLastKnownLocationPassiveProvider(locationManager)
            ?: getLastKnownLocationGpsProvider(locationManager)
            ?: getLastKnownLocationNetworkProvider(locationManager)

        return location?.let {
            Coordinates(
                location.latitude,
                location.longitude
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocationPassiveProvider(locationManager: LocationManager) =
        if (locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
            locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
        } else {
            null
        }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocationNetworkProvider(locationManager: LocationManager) =
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        } else {
            null
        }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocationGpsProvider(locationManager: LocationManager) =
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        } else {
            null
        }

    override fun startUpdateLocationListener(): Single<Coordinates> {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(location: LocationResult?) {
                    fusedLocationClient.removeLocationUpdates(this)
                    try {
                        subject.onSuccess(
                            Coordinates(
                                location!!.lastLocation.latitude,
                                location.lastLocation.longitude
                            )
                        )
                    } catch (exception: Exception) {
                        subject.onError(exception)
                    }
                }
            },
            Looper.myLooper()
        )
        return subject
    }
}