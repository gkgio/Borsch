package com.gkgio.data.address.repository

import com.gkgio.data.BaseTransformer
import com.gkgio.data.address.adding.CoordinatesRequest
import com.gkgio.domain.location.Coordinates
import javax.inject.Inject

class CoordinatesRepositoryDataResponseTransformer @Inject constructor() :
    BaseTransformer<CoordinatesRequest, Coordinates> {

    override fun transform(data: CoordinatesRequest) = with(data) {
        Coordinates(
            lat,
            lon
        )
    }
}