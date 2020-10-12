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
                    id = null,
                    city = addressesAddingRequest.city,
                    country = addressesAddingRequest.country,
                    flat = addressesAddingRequest.flat,
                    house = addressesAddingRequest.house,
                    location = addressesAddingRequest.location,
                    street = addressesAddingRequest.street,
                    block = addressesAddingRequest.block,
                    cityArea = addressesAddingRequest.cityArea,
                    region = addressesAddingRequest.region,
                    cityDistrict = addressesAddingRequest.cityDistrict
                )
            )
        }

    override fun getSavedAddresses(): Single<List<Address>> =
        addressesRepository.getSavedAddresses()

    override fun getLastSavedAddress(): Single<Address> =
        addressesRepository.getSavedAddresses().flatMap { Single.fromCallable { it[0] } }
}