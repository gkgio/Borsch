package com.gkgio.domain.address

import io.reactivex.Single

interface AddressesService {
    fun loadGeoSuggestions(geoSuggestionsRequest: GeoSuggestionsRequest): Single<GeoSuggestionsList>

    fun addSelectedAddress(addressAddingRequest: AddressAddingRequest): Single<Address>
}