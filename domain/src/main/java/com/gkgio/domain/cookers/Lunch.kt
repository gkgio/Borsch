package com.gkgio.domain.cookers

import java.math.BigDecimal

data class Lunch(
    val available: Boolean,
    val availableTimeFrom: String?,
    val availableTimeTo: String?,
    val discountPercent: Int,
    val id: String,
    val imageUrl: String?,
    val meals: List<Meal>,
    val name: String,
    val price: BigDecimal,
    val calories: String?,
    val weight: Int,
    val portions: Int
)