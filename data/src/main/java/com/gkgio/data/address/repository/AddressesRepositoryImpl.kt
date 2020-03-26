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
    private val addressAddingDataRequestTransformer: AddressAddingDataRequestTransformer,
    private val addressRepositoryDataResponseTransformer: AddressRepositoryDataResponseTransformer
) : AddressesRepository {

    companion object {
        const val KEY_LAST_KNOWN_ADDRESS = "lastKnownAddress"
    }

    override fun getSavedAddresses(): Single<List<Address>> =
        Single.fromCallable {
            getListAddresses()?.map { addressRepositoryDataResponseTransformer.transform(it) }
        }

    override fun saveLastKnownAddress(addressAddingRequest: AddressAddingRequest): Completable =
        Completable.fromCallable {
            var addressesList = getListAddresses()
            if (addressesList != null) {
                addressesList.toMutableList()
                    .add(addressAddingDataRequestTransformer.transform(addressAddingRequest))
            } else {
                addressesList = mutableListOf()
                addressesList.add(addressAddingDataRequestTransformer.transform(addressAddingRequest))
            }
            val type =
                Types.newParameterizedType(List::class.java, AddressAddingDataRequest::class.java)
            prefs.edit()
                .putString(
                    KEY_LAST_KNOWN_ADDRESS,
                    moshi.adapter<List<AddressAddingDataRequest>>(type)
                        .toJson(addressesList)
                ).apply()
        }

    private fun getListAddresses(): List<AddressAddingDataRequest>? {
        val addressJsonString = prefs.getString(KEY_LAST_KNOWN_ADDRESS, null)
        val type =
            Types.newParameterizedType(List::class.java, AddressAddingDataRequest::class.java)
        addressJsonString?.let {
            moshi.adapter<List<AddressAddingDataRequest>>(type).fromJson(addressJsonString)
        }
        return null
    }

}