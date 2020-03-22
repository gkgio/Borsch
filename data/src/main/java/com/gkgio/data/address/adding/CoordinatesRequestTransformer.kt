package com.gkgio.data.address.adding

import com.gkgio.data.BaseTransformer
import com.gkgio.data.address.adding.CoordinatesRequest
import com.gkgio.domain.location.Coordinates
import javax.inject.Inject

class CoordinatesRequestTransformer @Inject constructor() :
    BaseTransformer<Coordinates, CoordinatesRequest> {

    override fun transform(data: Coordinates) = with(data) {
        CoordinatesRequest(
            latitude,
            longitude
        )
    }
}