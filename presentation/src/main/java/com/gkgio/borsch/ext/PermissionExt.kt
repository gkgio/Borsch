package com.gkgio.borsch.ext

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable

fun Context.checkPermissionGranted(permission: String) =
    ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED

fun Context.checkLocationGranted() =
    checkPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)

fun Context.checkWriteStorageGranted() =
    checkPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)

fun Fragment.requestLocationPermission(): Observable<Boolean> =
    RxPermissions(this)
        .request(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

fun Fragment.requestWriteStoragePermission(): Observable<Boolean> =
    RxPermissions(this)
        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)