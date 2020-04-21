package com.gkgio.borsch.orders

import com.gkgio.domain.address.Address
import com.gkgio.domain.basket.BasketLunch
import com.gkgio.domain.basket.BasketMeal


data class OrderDataUi(
    val acceptedAt: String?,
    val chatId: String,
    val clientAddress: Address?,
    val clientId: String,
    val completedAt: String?,
    val cookerAddress: Address?,
    val cookerId: String,
    val createdAt: String,
    val estimatedCookTime: Long?,
    val meals: List<BasketMeal>?,
    val lunches: List<BasketLunch>?,
    val orderId: String,
    val slug: String?,
    val orderPrice: String,
    val status: String,
    val type: OrderStatus // pickup, delivery
)