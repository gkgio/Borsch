package com.gkgio.domain.address

import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface LoadAddressesUseCase {
    fun loadGeoSuggestions(geoSuggestionsRequest: GeoSuggestionsRequest): Single<GeoSuggestionsList>
    fun addNewClientAddress(addressesAddingRequest: AddressAddingRequest): Completable
    fun getSavedAddresses(): Single<List<Address>>
}

class LoadAddressesUseCaseImpl @Inject constructor(
    private val addressesService: AddressesService,
    private val addressesRepository: AddressesRepository
) : LoadAddressesUseCase {
    override fun loadGeoSuggestions(geoSuggestionsRequest: GeoSuggestionsRequest): Single<GeoSuggestionsList> =
        addressesService.loadGeoSuggestions(geoSuggestionsRequest)

    override fun addNewClientAddress(addressesAddingRequest: AddressAddingRequest): Completable =
        addressesService.addSelectedAddress(addressesAddingRequest)
            .flatMapCompletable {
                addressesRepository.saveLastKnownAddress(addressesAddingRequest)
            }

    override fun getSavedAddresses(): Single<List<Address>> =
        addressesRepository.getSavedAddresses()
}