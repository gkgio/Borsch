package com.gkgio.borsch.cookers.detail

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.location.Coordinates
import javax.inject.Inject

class CoordinatesUiTransformer @Inject constructor(
) : BaseTransformer<Coordinates, CoordinatesUi> {

    override fun transform(data: Coordinates) = with(data) {
        CoordinatesUi(
            latitude,
            longitude
        )
    }
}