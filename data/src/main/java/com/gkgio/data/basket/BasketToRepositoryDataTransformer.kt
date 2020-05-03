package com.gkgio.data.basket

import com.gkgio.data.BaseTransformer
import com.gkgio.data.cookers.CookersDataRequest
import com.gkgio.domain.basket.BasketData
import com.gkgio.domain.cookers.CookersRequest
import javax.inject.Inject


class BasketToRepositoryDataTransformer @Inject constructor() :
    BaseTransformer<BasketData, BasketRepositoryData> {

    override fun transform(data: BasketData) = with(data) {
        BasketRepositoryData(
            name,
            id,
            count,
            imageUrl,
            price.toString(),
            priceOneItem.toString(),
            type
        )
    }
}