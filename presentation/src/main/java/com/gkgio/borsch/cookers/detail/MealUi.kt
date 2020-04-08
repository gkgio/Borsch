package com.gkgio.borsch.cookers.detail

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class MealUi(
    val available: Boolean?,
    val calories: Int,
    val cookTime: Int,
    val description: String,
    val id: String,
    val imageUrl: String,
    val ingredients: List<String>,
    val name: String,//Meal name
    val portions: Int,//Amount of available portions
    val price: String,
    val tags: List<String>,
    val type: String?,
    val weight: String,
    val purePrice: BigDecimal
) : Parcelable