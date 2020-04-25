package com.gkgio.domain.basket

import com.gkgio.domain.address.Address
import com.gkgio.domain.cookers.Lunch
import com.gkgio.domain.cookers.Meal
import java.math.BigDecimal

data class OrderDetail(
    val acceptedAt: String?,
    val chatId: String,
    val clientAddress: Address?,
    val clientId: String,
    val completedAt: String?,
    val cookerAddress: Address?,
    val cookerId: String,
    val createdAt: String,
    val estimatedCookTime: Long?,
    val meals: List<Meal>?,
    val lunches: List<Lunch>?,
    val orderId: String,
    val slug: String?,
    val orderPrice: BigDecimal,
    val status: String,
    val type: String // pickup, delivery
)