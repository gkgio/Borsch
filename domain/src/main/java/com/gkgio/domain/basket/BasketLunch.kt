package com.gkgio.domain.basket

data class BasketLunch(
    val id: String,
    val imageUrl: String?,
    val meals: List<BasketMeal>
)