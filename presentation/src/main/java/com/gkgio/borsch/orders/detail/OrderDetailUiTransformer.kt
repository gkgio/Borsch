package com.gkgio.borsch.orders.detail

import com.gkgio.borsch.orders.OrderDataUi
import com.gkgio.borsch.orders.OrderStatus
import com.gkgio.borsch.utils.PriceFormatter
import com.gkgio.borsch.utils.WeightFormatter
import com.gkgio.data.BaseTransformer
import com.gkgio.domain.basket.OrderData
import com.gkgio.domain.basket.OrderDetail
import com.gkgio.domain.cookers.Lunch
import com.gkgio.domain.cookers.Meal
import javax.inject.Inject

class OrderDetailUiTransformer @Inject constructor(
    private val priceFormatter: PriceFormatter,
    private val weightFormatter: WeightFormatter
) : BaseTransformer<OrderDetail, OrderDetailUi> {

    override fun transform(data: OrderDetail) = with(data) {
        OrderDetailUi(
            acceptedAt,
            chatId,
            clientAddress,
            clientId,
            completedAt,
            cookerAddress,
            cookerId,
            createdAt,
            estimatedCookTime,
            getFoodsList(meals, lunches),
            orderId,
            slug,
            priceFormatter.format(orderPrice),
            status,
            getStatusEnum(type)
        )
    }

    private fun getStatusEnum(type: String) =
        when (type) {
            OrderStatus.COMPLETED.type -> OrderStatus.COMPLETED
            OrderStatus.CREATED.type -> OrderStatus.CREATED
            OrderStatus.ACCEPTED.type -> OrderStatus.ACCEPTED
            OrderStatus.COOKING.type -> OrderStatus.COOKING
            OrderStatus.REJECTED.type -> OrderStatus.REJECTED
            OrderStatus.CANCELED.type -> OrderStatus.CANCELED
            else -> OrderStatus.REJECTED
        }

    private fun getFoodsList(meals: List<Meal>?, lunches: List<Lunch>?): List<OrderFoodUi>? {
        val listFoods = mutableListOf<OrderFoodUi>()
        lunches?.forEach { lunchesList ->
            listFoods.add(
                OrderFoodUi(
                    lunchesList.imageUrl,
                    lunchesList.name,
                    priceFormatter.format(lunchesList.price),
                    lunchesList.weight?.let { weightFormatter.formatToOnePotion(it) }
                )
            )
        }
        meals?.forEach { mealsList ->
            listFoods.add(
                OrderFoodUi(
                    mealsList.imageUrl,
                    mealsList.name,
                    priceFormatter.format(mealsList.price),
                    mealsList.weight?.let { weightFormatter.formatToOnePotion(it) }
                )
            )
        }
        return if (listFoods.isEmpty()) null else listFoods
    }
}