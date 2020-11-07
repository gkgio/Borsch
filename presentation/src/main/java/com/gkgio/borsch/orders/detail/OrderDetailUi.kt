package com.gkgio.borsch.orders.detail

import com.gkgio.borsch.orders.OrderStatus
import com.gkgio.data.address.adding.CoordinatesResponse
import com.gkgio.domain.address.Address
import com.gkgio.domain.location.Coordinates
import com.squareup.moshi.Json

data class OrderDetailUi(
    val acceptedAt: String?,
    val chatId: String,
    val clientAddress: Address?,
    val clientId: String,
    val completedAt: String?,
    val cookerAddress: Address?,
    val cookerId: String,
    val createdAt: String?,
    val estimatedCookTime: Long?,
    val foods: List<OrderFoodUi>?,
    val orderId: String,
    val slug: String?,
    val orderPrice: String,
    val status: String,
    val type: OrderStatus? // pickup, delivery
)