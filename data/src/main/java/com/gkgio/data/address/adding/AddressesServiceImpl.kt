package com.gkgio.data.address.adding

import com.gkgio.data.address.GeoSuggestionsDataRequest
import com.gkgio.data.address.GeoSuggestionsDataRequestTransformer
import com.gkgio.data.address.GeoSuggestionsListResponse
import com.gkgio.data.address.GeoSuggestionsListResponseTransformer
import com.gkgio.data.base.BaseService
import com.gkgio.data.exception.ServerExceptionTransformer
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
    private val addressResponseTransformer: AddressResponseTransformer,
    serverExceptionTransformer: ServerExceptionTransformer
) : BaseService(serverExceptionTransformer), AddressesService {

    override fun loadGeoSuggestions(geoSuggestionsRequest: GeoSuggestionsRequest): Single<GeoSuggestionsList> =
        executeRequest(
            addressServiceApi.getGeoSuggestions(
                geoSuggestionsDataRequestTransformer.transform(
                    geoSuggestionsRequest
                )
            ).map { geoSuggestionsListResponseTransformer.transform(it) }
        )

    override fun addSelectedAddress(addressAddingRequest: AddressAddingRequest): Single<Address> =
        executeRequest(
            addressServiceApi.addSelectedAddress(
                addressAddingDataRequestTransformer.transform(
                    addressAddingRequest
                )
            ).map {
                addressResponseTransformer.transform(it)
            }
        )

    interface AddressServiceApi {
        @POST("geo/suggestions")
        fun getGeoSuggestions(@Body geoSuggestionsDataRequest: GeoSuggestionsDataRequest): Single<GeoSuggestionsListResponse>

        @POST("client/addresses/add")
        fun addSelectedAddress(@Body addressAddingDataRequest: AddressAddingDataRequest): Single<AddressResponse>
    }

}