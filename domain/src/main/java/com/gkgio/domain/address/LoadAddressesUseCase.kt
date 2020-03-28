package com.gkgio.domain.address

import com.gkgio.domain.auth.AuthUseCase
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface LoadAddressesUseCase {
    fun loadGeoSuggestions(geoSuggestionsRequest: GeoSuggestionsRequest): Single<GeoSuggestionsList>
    fun addNewClientAddress(addressesAddingRequest: AddressAddingRequest): Completable
    fun getSavedAddresses(): Single<List<Address>>
    fun getLastSavedAddress(): Single<Address>
}

class LoadAddressesUseCaseImpl @Inject constructor(
    private val addressesService: AddressesService,
    private val addressesRepository: AddressesRepository,
    private val authUseCase: AuthUseCase
) : LoadAddressesUseCase {
    override fun loadGeoSuggestions(geoSuggestionsRequest: GeoSuggestionsRequest): Single<GeoSuggestionsList> =
        addressesService.loadGeoSuggestions(geoSuggestionsRequest)

    override fun addNewClientAddress(addressesAddingRequest: AddressAddingRequest): Completable =
        if (authUseCase.getAuthToken() != null) {
            addressesService.addSelectedAddress(addressesAddingRequest)
                .flatMapCompletable {
                    addressesRepository.saveLastKnownAddress(it)
                }
        } else {
            addressesRepository.saveLastKnownAddress(
                Address(
                    null,
                    addressesAddingRequest.city,
                    addressesAddingRequest.country,
                    addressesAddingRequest.flat,
                    addressesAddingRequest.house,
                    addressesAddingRequest.location,
                    addressesAddingRequest.street,
                    addressesAddingRequest.block
                )
            )
        }

    override fun getSavedAddresses(): Single<List<Address>> =
        addressesRepository.getSavedAddresses()

    override fun getLastSavedAddress(): Single<Address> =
        addressesRepository.getSavedAddresses().flatMap { Single.fromCallable { it[0] } }
}