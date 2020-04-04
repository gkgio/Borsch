package com.gkgio.borsch.cookers.detail

import android.os.Parcelable
import com.gkgio.domain.cookers.Lunch
import com.gkgio.domain.cookers.Meal
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CookerDetailUi(
    val banned: Boolean?,
    val certified: Boolean,
    val commission: String?,
    val id: String,
    val meals: List<MealUi>,
    val lunches: List<LunchUi>?,
    val name: String,
    val paid: String?,
    val phone: String?,
    val rating: String,
    val suspended: Boolean?,
    val verified: Boolean?,
    val avatarUrl: String?,
    val delivery: Boolean,
    val countryTags: List<String>?,
    val description: String,
    val onDuty: Boolean? //Whether user is online and accepting orders
) : Parcelable