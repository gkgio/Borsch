package com.gkgio.data.address

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.address.GeoSuggestionData
import javax.inject.Inject

class GeoSuggestionDataResponseTransformer @Inject constructor() :
    BaseTransformer<GeoSuggestionDataResponse, GeoSuggestionData> {

    override fun transform(data: GeoSuggestionDataResponse) = with(data) {
        GeoSuggestionData(
            city,
            city_area,
            city_district,
            city_type_full,
            country,
            flat,
            geo_lat,
            geo_lon,
            house,
            region
        )
    }
}