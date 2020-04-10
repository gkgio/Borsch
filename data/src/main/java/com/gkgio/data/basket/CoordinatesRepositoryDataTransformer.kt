package com.gkgio.data.basket

import com.gkgio.data.BaseTransformer
import com.gkgio.data.address.adding.CoordinatesResponse
import com.gkgio.data.cookers.detail.CookerAddressResponse
import com.gkgio.domain.cookers.detail.CookerAddress
import com.gkgio.domain.location.Coordinates
import javax.inject.Inject

class CoordinatesRepositoryDataTransformer @Inject constructor() :
    BaseTransformer<Coordinates, CoordinatesResponse> {

    override fun transform(data: Coordinates) = with(data) {
        CoordinatesResponse(
            latitude,
            longitude
        )
    }
}