package com.gkgio.data.cookers

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.cookers.CookersRequest
import javax.inject.Inject

class CookersDataRequestTransformer @Inject constructor() :
    BaseTransformer<CookersRequest, CookersDataRequest> {

    override fun transform(data: CookersRequest) = with(data) {
        CookersDataRequest(
            addressId,
            distance,
            targets
        )
    }
}