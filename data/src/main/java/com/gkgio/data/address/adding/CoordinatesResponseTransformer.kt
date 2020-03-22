package com.gkgio.data.address.adding

import com.gkgio.data.BaseTransformer
import com.gkgio.data.address.adding.CoordinatesResponse
import com.gkgio.domain.location.Coordinates
import javax.inject.Inject

class CoordinatesResponseTransformer @Inject constructor() :
    BaseTransformer<CoordinatesResponse, Coordinates> {

    override fun transform(data: CoordinatesResponse) = with(data) {
        Coordinates(
            lat,
            lon
        )
    }
}