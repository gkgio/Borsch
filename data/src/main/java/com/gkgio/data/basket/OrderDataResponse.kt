package com.gkgio.data.basket

import com.gkgio.data.address.adding.AddressResponse
import com.gkgio.data.cookers.LunchResponse
import com.gkgio.data.cookers.MealResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderDataResponse(
    @Json(name = "accepted_at")
    val acceptedAt: String?,
    @Json(name = "chat_id")
    val chatId: String,
    @Json(name = "client_address")
    val clientAddress: AddressResponse?,
    @Json(name = "client_id")
    val clientId: String,
    @Json(name = "completed_at")
    val completedAt: String?,
    @Json(name = "cooker_address")
    val cookerAddress: AddressResponse?,
    @Json(name = "cooker_id")
    val cookerId: String,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "estimated_cook_time")
    val estimatedCookTime: Long?,
    @Json(name = "meals")
    val meals: List<BasketMealResponse>?,
    @Json(name = "lunches")
    val lunches: List<BasketLunchResponse>?,
    @Json(name = "order_id")
    val orderId: String,
    @Json(name = "order_price")
    val orderPrice: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "type")
    val type: String // pickup, delivery
)