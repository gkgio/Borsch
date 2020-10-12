package com.gkgio.domain.basket

data class BasketOrderRequest(
    val mealsIds: List<String>?,
    val lunchesIds: List<String>?,
    val cookerId: String
)