package com.gkgio.domain.cookers

import com.gkgio.domain.cookers.detail.CookerAddress

data class Cooker(
    val banned: Boolean?,
    val certified: Boolean,
    val id: String,
    val meals: List<Meal>,
    val lunches: List<Lunch>?,
    val name: String?,
    val phone: String?,
    val rating: Double?,
    val suspended: Boolean?,
    val verified: Boolean?,
    val avatarUrl: String?,
    val delivery: Boolean,
    val countryTags: List<String>?,
    val description: String?,
    val onDuty: Boolean, //Whether user is online and accepting orders
    val distance: Int?,
    val cookerAddress: CookerAddress?
)