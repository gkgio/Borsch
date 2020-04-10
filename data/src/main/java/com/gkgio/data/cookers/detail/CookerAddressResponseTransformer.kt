package com.gkgio.data.cookers.detail

import com.gkgio.data.BaseTransformer
import com.gkgio.data.address.adding.CoordinatesResponseTransformer
import com.gkgio.domain.cookers.detail.CookerAddress
import com.gkgio.domain.cookers.detail.CookerDetail
import javax.inject.Inject

class CookerAddressResponseTransformer @Inject constructor(
    private val coordinatesResponseTransformer: CoordinatesResponseTransformer
) : BaseTransformer<CookerAddressResponse, CookerAddress> {

    override fun transform(data: CookerAddressResponse) = with(data) {
        CookerAddress(
            street,
            house,
            flat,
            floor,
            coordinatesResponseTransformer.transform(coordinates)
        )
    }
}