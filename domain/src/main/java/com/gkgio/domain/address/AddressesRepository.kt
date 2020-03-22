package com.gkgio.domain.address

import io.reactivex.Completable
import io.reactivex.Single

interface AddressesRepository {
    fun getLastKnownAddress(): Single<Address>
    fun saveLastKnownAddress(addressAddingRequest: AddressAddingRequest): Completable
}