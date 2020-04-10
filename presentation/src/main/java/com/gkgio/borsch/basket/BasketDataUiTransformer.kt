package com.gkgio.borsch.basket

import com.gkgio.borsch.utils.PriceFormatter
import com.gkgio.data.BaseTransformer
import com.gkgio.domain.basket.BasketData
import javax.inject.Inject

class BasketDataUiTransformer @Inject constructor(
    private val priceFormatter: PriceFormatter
) : BaseTransformer<BasketData, BasketDataUi> {

    override fun transform(data: BasketData) = with(data) {
        BasketDataUi(
            name,
            id,
            count,
            priceFormatter.format(price),
            priceOneItem
        )
    }
}