package com.gkgio.data.address.repository

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.address.Address
import javax.inject.Inject

class AddressRepositoryDataResponseTransformer @Inject constructor(
    private val coordinatesRepositoryDataResponseTransformer: CoordinatesRepositoryDataResponseTransformer
) : BaseTransformer<AddressRepositoryRequest, Address> {

    override fun transform(data: AddressRepositoryRequest) = with(data) {
        Address(
            id,
            city,
            country,
            flat,
            house,
            coordinatesRepositoryDataResponseTransformer.transform(location),
            street,
            block
        )
    }
}