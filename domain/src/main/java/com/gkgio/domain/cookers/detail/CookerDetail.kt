package com.gkgio.domain.cookers.detail

import com.gkgio.domain.cookers.Lunch
import com.gkgio.domain.cookers.Meal

data class CookerDetail(
    val banned: Boolean?,
    val certified: Boolean,
    val commission: String?,
    val id: String,
    val meals: List<Meal>,
    val lunches: List<Lunch>?,
    val name: String,
    val paid: String?,
    val phone: String?,
    val rating: String,
    val suspended: Boolean?,
    val verified: Boolean?,
    val avatarUrl: String?,
    val delivery: Boolean,
    val countryTags: List<String>?,
    val description: String?,
    val onDuty: Boolean? //Whether user is online and accepting orders
)