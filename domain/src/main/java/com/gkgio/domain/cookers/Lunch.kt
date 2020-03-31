package com.gkgio.domain.cookers

data class Lunch(
    val available: Boolean,
    val availableTimeFrom: String?,
    val availableTimeTo: String?,
    val discountPercent: Int,
    val id: String,
    val imageUrl: String?,
    val meals: List<Meal>
)