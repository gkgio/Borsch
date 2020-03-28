package com.gkgio.data.address.repository

import android.content.SharedPreferences
import com.gkgio.data.address.adding.AddressAddingDataRequest
import com.gkgio.data.address.adding.AddressAddingDataRequestTransformer
import com.gkgio.domain.address.Address
import com.gkgio.domain.address.AddressAddingRequest
import com.gkgio.domain.address.AddressesRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class AddressesRepositoryImpl @Inject constructor(
    private val prefs: SharedPreferences,
    private val moshi: Moshi,
    private val addressAddingDataRequestTransformer: AddressRepositoryRequestTransformer,
    private val addressRepositoryDataResponseTransformer: AddressRepositoryDataResponseTransformer
) : AddressesRepository {

    companion object {
        const val KEY_LAST_KNOWN_ADDRESS = "lastKnownAddress"
    }

    override fun getSavedAddresses(): Single<List<Address>> =
        Single.fromCallable {
            getListAddresses()?.map { addressRepositoryDataResponseTransformer.transform(it) }
        }

    override fun saveLastKnownAddress(address: Address): Completable =
        Completable.fromCallable {
            var addressesList = getListAddresses()?.toMutableList()
            if (addressesList != null) {
                val newAddress = addressAddingDataRequestTransformer.transform(address)
                if (addressesList.contains(newAddress)) {
                    addressesList.remove(newAddress)
                }
                addressesList.add(0, newAddress)
                if (addressesList.size > 4) addressesList =
                    addressesList.dropLast(1).toMutableList()
            } else {
                addressesList = mutableListOf()
                addressesList.add(addressAddingDataRequestTransformer.transform(address))
            }
            val type =
                Types.newParameterizedType(List::class.java, AddressRepositoryRequest::class.java)
            prefs.edit()
                .putString(
                    KEY_LAST_KNOWN_ADDRESS,
                    moshi.adapter<List<AddressRepositoryRequest>>(type)
                        .toJson(addressesList)
                ).apply()
        }

    private fun getListAddresses(): List<AddressRepositoryRequest>? {
        val addressJsonString = prefs.getString(KEY_LAST_KNOWN_ADDRESS, null)
        val type =
            Types.newParameterizedType(List::class.java, AddressRepositoryRequest::class.java)
        addressJsonString?.let {
            return moshi.adapter<List<AddressRepositoryRequest>>(type).fromJson(addressJsonString)
        }
        return null
    }

}