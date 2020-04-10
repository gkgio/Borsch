package com.gkgio.borsch.cookers.detail

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoordinatesUi(
    val latitude: Double,
    val longitude: Double
) : Parcelable