package com.gkgio.domain.cookers

import java.math.BigDecimal

data class Meal(
    val available: Boolean?,
    val calories: Int?,
    val cookTime: Int?,
    val description: String?,
    val id: String,
    val imageUrl: String?,
    val ingredients: List<String>?,
    val name: String,//Meal name
    val portions: Int,//Amount of available portions
    val price: BigDecimal,
    val tags: List<String>,
    val type: String?,
    val weight: Int
)