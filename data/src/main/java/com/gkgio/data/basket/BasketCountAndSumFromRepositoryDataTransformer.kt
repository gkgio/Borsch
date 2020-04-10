package com.gkgio.data.basket

import com.gkgio.data.BaseTransformer
import com.gkgio.data.cookers.detail.CookerAddressResponseTransformer
import com.gkgio.domain.basket.BasketCountAndSum
import java.math.BigDecimal
import javax.inject.Inject

class BasketCountAndSumFromRepositoryDataTransformer @Inject constructor(
    private val cookerAddressResponseTransformer: CookerAddressResponseTransformer
) : BaseTransformer<BasketCountAndSumRepositoryData, BasketCountAndSum> {

    override fun transform(data: BasketCountAndSumRepositoryData) = with(data) {
        BasketCountAndSum(
            count,
            BigDecimal(sum),
            cookerId,
            cookerAddress?.let { cookerAddressResponseTransformer.transform(it) }
        )
    }
}