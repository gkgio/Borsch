package com.gkgio.data.address.repository

import android.content.SharedPreferences
import com.gkgio.data.address.adding.AddressAddingDataRequest
import com.gkgio.data.address.adding.AddressAddingDataRequestTransformer
import com.gkgio.domain.address.Address
import com.gkgio.domain.address.AddressAddingRequest
import com.gkgio.domain.address.AddressesRepository
import com.squareup.moshi.Moshi
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

    override fun getLastKnownAddress(): Single<Address> =
        Single.fromCallable {
            val addressJsonString = prefs.getString(KEY_LAST_KNOWN_ADDRESS, null)
            val addressObject =
                moshi.adapter(AddressAddingDataRequest::class.java).fromJson(addressJsonString!!)
            addressRepositoryDataResponseTransformer.transform(addressObject!!)
        }

    override fun saveLastKnownAddress(addressAddingRequest: AddressAddingRequest): Completable =
        Completable.fromCallable {
            prefs.edit()
                .putString(
                    KEY_LAST_KNOWN_ADDRESS,
                    moshi.adapter(AddressAddingDataRequest::class.java)
                        .toJson(addressAddingDataRequestTransformer.transform(addressAddingRequest))
                ).apply()
        }


}