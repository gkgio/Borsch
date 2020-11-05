package com.gkgio.borsch.cookers.detail

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CookerAddressUi(
    val street: String,
    val house: String,
    val flat: String?,
    val floor: String?,
    val coordinates: CoordinatesUi
) : Parcelable