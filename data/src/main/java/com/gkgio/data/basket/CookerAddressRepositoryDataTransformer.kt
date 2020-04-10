package com.gkgio.data.basket

import com.gkgio.data.BaseTransformer
import com.gkgio.data.cookers.detail.CookerAddressResponse
import com.gkgio.domain.basket.BasketCountAndSum
import com.gkgio.domain.cookers.detail.CookerAddress
import javax.inject.Inject

class CookerAddressRepositoryDataTransformer @Inject constructor(
    private val coordinatesRepositoryDataTransformer: CoordinatesRepositoryDataTransformer
) : BaseTransformer<CookerAddress, CookerAddressResponse> {

    override fun transform(data: CookerAddress) = with(data) {
        CookerAddressResponse(
            street,
            house,
            flat,
            floor,
            coordinatesRepositoryDataTransformer.transform(coordinates)
        )
    }
}