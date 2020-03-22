package com.gkgio.domain.address

import io.reactivex.Single
import javax.inject.Inject

interface LoadAddressesUseCase {
    fun loadGetSuggestions(geoSuggestionsRequest: GeoSuggestionsRequest): Single<GeoSuggestionsList>
    fun addNewClientAddress(addressesAddingRequest: AddressAddingRequest): Single<Address>
    fun getLastKnownAddress(): Single<Address>
}

class LoadAddressesUseCaseImpl @Inject constructor(
    private val addressesService: AddressesService,
    private val addressesRepository: AddressesRepository
) : LoadAddressesUseCase {
    override fun loadGetSuggestions(geoSuggestionsRequest: GeoSuggestionsRequest): Single<GeoSuggestionsList> =
        addressesService.loadGetSuggestions(geoSuggestionsRequest)

    override fun addNewClientAddress(addressesAddingRequest: AddressAddingRequest): Single<Address> =
        addressesService.addSelectedAddress(addressesAddingRequest)
            .flatMap {
                addressesRepository.saveLastKnownAddress(addressesAddingRequest)
                Single.fromCallable { it }
            }

    override fun getLastKnownAddress(): Single<Address> =
        addressesRepository.getLastKnownAddress()
}