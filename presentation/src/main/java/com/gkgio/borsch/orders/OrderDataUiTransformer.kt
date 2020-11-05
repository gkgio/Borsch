package com.gkgio.borsch.orders

import com.gkgio.borsch.utils.PriceFormatter
import com.gkgio.data.BaseTransformer
import com.gkgio.domain.basket.OrderData
import javax.inject.Inject

class OrderDataUiTransformer @Inject constructor(
    private val priceFormatter: PriceFormatter
) : BaseTransformer<OrderData, OrderDataUi> {

    override fun transform(data: OrderData) = with(data) {
        OrderDataUi(
            acceptedAt,
            chatId,
            clientAddress,
            clientId,
            completedAt,
            cookerAddress,
            cookerId,
            createdAt,
            estimatedCookTime,
            meals,
            lunches,
            orderId,
            slug,
            priceFormatter.format(orderPrice),
            status,
            type?.let { getStatusEnum(it) }
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
}