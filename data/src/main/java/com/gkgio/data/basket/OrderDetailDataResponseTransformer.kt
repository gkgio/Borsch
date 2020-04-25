package com.gkgio.data.basket

import com.gkgio.data.BaseTransformer
import com.gkgio.data.address.adding.AddressResponseTransformer
import com.gkgio.data.cookers.CookerResponseTransformer
import com.gkgio.data.cookers.LunchResponseTransformer
import com.gkgio.data.cookers.MealResponseTransformer
import com.gkgio.domain.basket.OrderData
import com.gkgio.domain.basket.OrderDetailData
import java.math.BigDecimal
import javax.inject.Inject

class OrderDetailDataResponseTransformer @Inject constructor(
    private val cookerResponseTransformer: CookerResponseTransformer,
    private val orderDetailResponseTransformer: OrderDetailResponseTransformer
) : BaseTransformer<OrderDetailDataResponse, OrderDetailData> {

    override fun transform(data: OrderDetailDataResponse) = with(data) {
        OrderDetailData(
            cookerResponseTransformer.transform(cooker),
            orderDetailResponseTransformer.transform(order)
        )
    }
}