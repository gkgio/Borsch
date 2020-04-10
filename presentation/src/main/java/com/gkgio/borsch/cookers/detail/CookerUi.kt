package com.gkgio.borsch.cookers.detail

data class CookerUi(
    val banned: Boolean?,
    val certified: Boolean,
    val id: String,
    val meals: List<MealUi>,
    val lunches: List<LunchUi>?,
    val name: String,
    val phone: String?,
    val rating: String,
    val suspended: Boolean?,
    val verified: Boolean?,
    val avatarUrl: String?,
    val delivery: Boolean,
    val countryTags: List<String>?,
    val description: String?,
    val onDuty: Boolean, //Whether user is online and accepting orders
    val distance: String?,
    val cookerAddress: CookerAddressUi?
)