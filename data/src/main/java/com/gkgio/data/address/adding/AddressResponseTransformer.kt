package com.gkgio.data.address.adding

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.address.Address
import javax.inject.Inject

class AddressResponseTransformer @Inject constructor(
    private val coordinatesResponseTransformer: CoordinatesResponseTransformer
) : BaseTransformer<AddressResponse, Address> {

    override fun transform(data: AddressResponse) = with(data) {
        Address(
            city,
            country,
            flat,
            house,
            coordinatesResponseTransformer.transform(location),
            street
        )
    }
}