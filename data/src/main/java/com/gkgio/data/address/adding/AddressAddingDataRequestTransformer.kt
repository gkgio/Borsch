package com.gkgio.data.address.adding

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.address.AddressAddingRequest
import javax.inject.Inject

class AddressAddingDataRequestTransformer @Inject constructor(
    private val coordinatesRequestTransformer: CoordinatesRequestTransformer
) : BaseTransformer<AddressAddingRequest, AddressAddingDataRequest> {

    override fun transform(data: AddressAddingRequest) = with(data) {
        AddressAddingDataRequest(
            city,
            country,
            flat,
            house,
            coordinatesRequestTransformer.transform(location),
            street
        )
    }
}