package com.gkgio.domain.address

import io.reactivex.Single

interface AddressesService {
    fun loadGetSuggestions(geoSuggestionsRequest: GeoSuggestionsRequest): Single<GeoSuggestionsList>

    fun addSelectedAddress(addressAddingRequest: AddressAddingRequest): Single<Address>
}