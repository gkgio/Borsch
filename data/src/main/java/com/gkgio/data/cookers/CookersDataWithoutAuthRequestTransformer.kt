package com.gkgio.data.cookers

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.cookers.CookersWithoutAuthRequest
import javax.inject.Inject

class CookersDataWithoutAuthRequestTransformer @Inject constructor() :
    BaseTransformer<CookersWithoutAuthRequest, CookersDataWithoutAuthRequest> {

    override fun transform(data: CookersWithoutAuthRequest) = with(data) {
        CookersDataWithoutAuthRequest(
            lat,
            lon,
            distance,
            targets
        )
    }
}