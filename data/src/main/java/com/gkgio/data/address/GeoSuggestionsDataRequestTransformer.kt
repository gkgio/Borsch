package com.gkgio.data.address

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.address.GeoSuggestionsRequest
import javax.inject.Inject

class GeoSuggestionsDataRequestTransformer @Inject constructor() :
    BaseTransformer<GeoSuggestionsRequest, GeoSuggestionsDataRequest> {

    override fun transform(data: GeoSuggestionsRequest) = with(data) {
        GeoSuggestionsDataRequest(
            lat,
            lon,
            query
        )
    }
}