package com.gkgio.domain.address

import io.reactivex.Completable
import io.reactivex.Single

interface AddressesRepository {
    fun getSavedAddresses(): Single<List<Address>>
    fun saveLastKnownAddress(addressAddingRequest: AddressAddingRequest): Completable
}