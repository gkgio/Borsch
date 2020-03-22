package com.gkgio.data.address.adding

import com.gkgio.data.address.GeoSuggestionsDataRequest
import com.gkgio.data.address.GeoSuggestionsDataRequestTransformer
import com.gkgio.data.address.GeoSuggestionsListResponse
import com.gkgio.data.address.GeoSuggestionsListResponseTransformer
import com.gkgio.domain.address.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject

class AddressesServiceImpl @Inject constructor(
    private val addressServiceApi: AddressServiceApi,
    private val geoSuggestionsDataRequestTransformer: GeoSuggestionsDataRequestTransformer,
    private val geoSuggestionsListResponseTransformer: GeoSuggestionsListResponseTransformer,
    private val addressAddingDataRequestTransformer: AddressAddingDataRequestTransformer,
    private val addressResponseTransformer: AddressResponseTransformer
) : AddressesService {

    override fun loadGetSuggestions(geoSuggestionsRequest: GeoSuggestionsRequest): Single<GeoSuggestionsList> =
        addressServiceApi.getGeoSuggestions(
            geoSuggestionsDataRequestTransformer.transform(
                geoSuggestionsRequest
            )
        ).map { geoSuggestionsListResponseTransformer.transform(it) }

    override fun addSelectedAddress(addressAddingRequest: AddressAddingRequest): Single<Address> =
        addressServiceApi.addSelectedAddress(
            addressAddingDataRequestTransformer.transform(
                addressAddingRequest
            )
        ).map {
            addressResponseTransformer.transform(it.address)
        }

    interface AddressServiceApi {
        @POST("geo/suggestions")
        fun getGeoSuggestions(@Body geoSuggestionsDataRequest: GeoSuggestionsDataRequest): Single<GeoSuggestionsListResponse>

        @POST("client/addresses/add")
        fun addSelectedAddress(@Body addressAddingDataRequest: AddressAddingDataRequest): Single<AddressDataResponse>
    }

}