package com.gkgio.borsch.location.saved

import com.gkgio.data.BaseTransformer
import com.gkgio.data.cookers.CookersDataRequest
import com.gkgio.domain.address.Address
import com.gkgio.domain.cookers.CookersRequest
import javax.inject.Inject

class AddressUiTransformer @Inject constructor() :
    BaseTransformer<Address, AddressUi> {

    override fun transform(data: Address) = with(data) {
        AddressUi(
            data,
            false
        )
    }
}