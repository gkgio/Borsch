package com.gkgio.borsch.orders.detail

import com.gkgio.borsch.cookers.detail.CookerUiTransformer
import com.gkgio.borsch.utils.PriceFormatter
import com.gkgio.data.BaseTransformer
import com.gkgio.domain.basket.OrderDetail
import com.gkgio.domain.basket.OrderDetailData
import javax.inject.Inject

class OrderDetailDataUiTransformer @Inject constructor(
    private val cookerUiTransformer: CookerUiTransformer,
    private val orderDetailUiTransformer: OrderDetailUiTransformer
) : BaseTransformer<OrderDetailData, OrderDetailDataUi> {

    override fun transform(data: OrderDetailData) = with(data) {
        OrderDetailDataUi(
            cookerUiTransformer.transform(cooker),
            orderDetailUiTransformer.transform(order)
        )
    }
}