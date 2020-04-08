package com.gkgio.borsch.cookers.detail

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class LunchUi(
    val available: Boolean,
    val availableTimeFrom: String?,
    val availableTimeTo: String?,
    val discountPercent: Int,
    val id: String,
    val imageUrl: String?,
    val meals: List<MealUi>,
    val name: String,
    val price: String,
    val calories: String,
    val weight: String,
    val pricePure: BigDecimal
) : Parcelable