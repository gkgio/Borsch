package com.gkgio.data.basket

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.basket.BasketData
import java.math.BigDecimal
import javax.inject.Inject

class BasketFromRepositoryDataTransformer @Inject constructor() :
    BaseTransformer<BasketRepositoryData, BasketData> {

    override fun transform(data: BasketRepositoryData) = with(data) {
        BasketData(
            name,
            id,
            count,
            BigDecimal(price),
            BigDecimal(priceOneItem),
            type
        )
    }
}