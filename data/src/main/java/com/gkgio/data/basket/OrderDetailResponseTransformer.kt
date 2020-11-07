package com.gkgio.data.basket

import com.gkgio.data.BaseTransformer
import com.gkgio.data.address.adding.AddressResponseTransformer
import com.gkgio.data.address.adding.CoordinatesResponseTransformer
import com.gkgio.data.cookers.LunchResponseTransformer
import com.gkgio.data.cookers.MealResponseTransformer
import com.gkgio.domain.basket.OrderDetail
import com.gkgio.domain.basket.OrderDetailData
import java.math.BigDecimal
import javax.inject.Inject

class OrderDetailResponseTransformer @Inject constructor(
    private val addressResponseTransformer: AddressResponseTransformer,
    private val mealsResponseTransformer: MealResponseTransformer,
    private val lunchResponseTransformer: LunchResponseTransformer
) : BaseTransformer<OrderDetailResponse, OrderDetail> {

    override fun transform(data: OrderDetailResponse) = with(data) {
        OrderDetail(
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
            slug,
            BigDecimal(orderPrice),
            status,
            type
        )
    }
}