package com.gkgio.data.address.repository

import com.gkgio.data.BaseTransformer
import com.gkgio.data.address.adding.AddressAddingDataRequest
import com.gkgio.domain.address.Address
import javax.inject.Inject

class AddressRepositoryDataResponseTransformer @Inject constructor(
    private val coordinatesRepositoryDataResponseTransformer: CoordinatesRepositoryDataResponseTransformer
) : BaseTransformer<AddressAddingDataRequest, Address> {

    override fun transform(data: AddressAddingDataRequest) = with(data) {
        Address(
            city,
            country,
            flat,
            house,
            coordinatesRepositoryDataResponseTransformer.transform(location),
            street
        )
    }
}