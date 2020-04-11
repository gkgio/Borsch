package com.gkgio.data.basket

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.basket.BasketOrderRequest
import javax.inject.Inject

class BasketOrderDataRequestTransformer @Inject constructor() :
    BaseTransformer<BasketOrderRequest, BasketOrderDataRequest> {

    override fun transform(data: BasketOrderRequest) = with(data) {
        BasketOrderDataRequest(
            mealsIds?.let { mealsIdsList -> mealsIdsList.map { BasketFoodIdRequest(it) } },
            lunchesIds?.let { lunchesIdsList -> lunchesIdsList.map { BasketFoodIdRequest(it) } },
            type
        )
    }
}