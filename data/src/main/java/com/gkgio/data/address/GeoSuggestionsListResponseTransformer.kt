package com.gkgio.data.address

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.address.GeoSuggestionsList
import javax.inject.Inject

class GeoSuggestionsListResponseTransformer @Inject constructor(
    private val geoSuggestionResponseTransformer: GeoSuggestionResponseTransformer
) : BaseTransformer<GeoSuggestionsListResponse, GeoSuggestionsList> {

    override fun transform(data: GeoSuggestionsListResponse) = with(data) {
        GeoSuggestionsList(
            lat,
            lon,
            query,
            suggestions.map { geoSuggestionResponseTransformer.transform(it) }
        )
    }
}