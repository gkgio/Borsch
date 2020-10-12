package com.gkgio.data.address.repository

import com.gkgio.data.BaseTransformer
import com.gkgio.data.address.adding.AddressAddingDataRequest
import com.gkgio.data.address.adding.CoordinatesRequestTransformer
import com.gkgio.domain.address.Address
import com.gkgio.domain.address.AddressAddingRequest
import javax.inject.Inject

class AddressRepositoryRequestTransformer @Inject constructor(
    private val coordinatesRequestTransformer: CoordinatesRequestTransformer
) : BaseTransformer<Address, AddressRepositoryRequest> {

    override fun transform(data: Address) = with(data) {
        val addressRequest = AddressRepositoryRequest(
            city = city,
            country = country,
            flat = flat,
            house = house,
            location = coordinatesRequestTransformer.transform(location),
            street = street,
            block = block,
            region = region,
            cityArea = cityArea,
            cityDistrict = cityDistrict
        )
        addressRequest.id = id
        addressRequest
    }
}