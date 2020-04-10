package com.gkgio.borsch.cookers.detail

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FoodItemRequest(
    val cookerId: String,
    val foodId: String,
    val type: Int,
    val cookerAddressUi: CookerAddressUi?
) : Parcelable