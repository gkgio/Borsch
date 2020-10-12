package com.gkgio.data.address.repository

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.address.Address
import javax.inject.Inject

class AddressRepositoryDataResponseTransformer @Inject constructor(
    private val coordinatesRepositoryDataResponseTransformer: CoordinatesRepositoryDataResponseTransformer
) : BaseTransformer<AddressRepositoryRequest, Address> {

    override fun transform(data: AddressRepositoryRequest) = with(data) {
        Address(
            id = id,
            city = city,
            country = country,
            flat = flat,
            house = house,
            location = coordinatesRepositoryDataResponseTransformer.transform(location),
            street = street,
            block = block,
            region = region,
            cityArea = cityArea,
            cityDistrict = cityDistrict
        )
    }
}