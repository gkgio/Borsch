package com.gkgio.borsch.cookers.detail

import com.gkgio.borsch.utils.PriceFormatter
import com.gkgio.data.BaseTransformer
import com.gkgio.domain.basket.BasketCountAndSum
import com.gkgio.domain.cookers.detail.CookerDetail
import javax.inject.Inject

class BasketCountAndSumUiTransformer @Inject constructor(
    private val priceFormatter: PriceFormatter
) : BaseTransformer<BasketCountAndSum, BasketCountAndSumUi> {

    override fun transform(data: BasketCountAndSum) = with(data) {
        BasketCountAndSumUi(
            count,
            priceFormatter.format(data.sum),
            cookerId
        )
    }
}