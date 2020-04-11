package com.gkgio.data.basket

import com.gkgio.data.BaseTransformer
import com.gkgio.data.address.adding.AddressResponseTransformer
import com.gkgio.data.cookers.LunchResponseTransformer
import com.gkgio.data.cookers.MealResponseTransformer
import com.gkgio.data.cookers.detail.CookerAddressResponse
import com.gkgio.domain.basket.OrderData
import com.gkgio.domain.cookers.detail.CookerAddress
import javax.inject.Inject

class OrderDataResponseTransformer @Inject constructor(
    private val addressResponseTransformer: AddressResponseTransformer,
    private val mealsResponseTransformer: BasketMealResponseTransformer,
    private val lunchResponseTransformer: BasketLunchResponseTransformer
) : BaseTransformer<OrderDataResponse, OrderData> {

    override fun transform(data: OrderDataResponse) = with(data) {
        OrderData(
            acceptedAt,
            chatId,
            clientAddress?.let { addressResponseTransformer.transform(it) },
            clientId,
            completedAt,
            cookerAddress?.let { addressResponseTransformer.transform(it) },
            cookerId,
            createdAt,
            estimatedCookTime,
            meals?.let { mealsList ->
                if (mealsList.isNotEmpty()) mealsList.map {
                    mealsResponseTransformer.transform(it)
                } else null
            },
            lunches?.let { lunchesList ->
                if (lunchesList.isNotEmpty()) lunchesList.map {
                    lunchResponseTransformer.transform(it)
                } else null
            },
            orderId,
            orderPrice,
            status,
            type
        )
    }
}