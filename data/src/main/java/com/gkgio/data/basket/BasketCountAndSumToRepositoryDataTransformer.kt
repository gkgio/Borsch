package com.gkgio.data.basket

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.basket.BasketCountAndSum
import javax.inject.Inject

class BasketCountAndSumToRepositoryDataTransformer @Inject constructor(
    private val cookerAddressRepositoryDataTransformer: CookerAddressRepositoryDataTransformer
) : BaseTransformer<BasketCountAndSum, BasketCountAndSumRepositoryData> {

    override fun transform(data: BasketCountAndSum) = with(data) {
        BasketCountAndSumRepositoryData(
            count,
            sum.toString(),
            cookerId,
            cookerAddress?.let { cookerAddressRepositoryDataTransformer.transform(it) }
        )
    }
}