package com.gkgio.data.basket

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.basket.BasketCountAndSum
import java.math.BigDecimal
import javax.inject.Inject

class BasketCountAndSumFromRepositoryDataTransformer @Inject constructor() :
    BaseTransformer<BasketCountAndSumRepositoryData, BasketCountAndSum> {

    override fun transform(data: BasketCountAndSumRepositoryData) = with(data) {
        BasketCountAndSum(
            count,
            BigDecimal(sum),
            cookerId
        )
    }
}