package com.gkgio.borsch.cookers.detail

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.cookers.detail.CookerAddress
import javax.inject.Inject

class CookerAddressUiTransformer @Inject constructor(
    private val coordinatesUiTransformer: CoordinatesUiTransformer
) : BaseTransformer<CookerAddress, CookerAddressUi> {

    override fun transform(data: CookerAddress) = with(data) {
        CookerAddressUi(
            street,
            house,
            flat,
            floor,
            coordinatesUiTransformer.transform(coordinates)
        )
    }
}