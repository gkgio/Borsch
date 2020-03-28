package com.gkgio.data.cookers

import com.gkgio.domain.cookers.Cooker
import com.gkgio.domain.cookers.CookersRequest
import com.gkgio.domain.cookers.CookersService
import com.gkgio.domain.cookers.CookersWithoutAuthRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject

class CookersServiceImpl @Inject constructor(
    private val cookersServiceApi: CookersServiceApi,
    private val cookerResponseTransformer: CookerResponseTransformer,
    private val cookersDataRequestTransformer: CookersDataRequestTransformer,
    private val cookersDataWithoutAuthRequestTransformer: CookersDataWithoutAuthRequestTransformer
) : CookersService {

    override fun loadCookersList(cookersRequest: CookersWithoutAuthRequest): Single<List<Cooker>> =
        cookersServiceApi.loadCookersListWithoutAuth(
                cookersDataWithoutAuthRequestTransformer.transform(
                    cookersRequest
                )
            )
            .map { cookersData ->
                cookersData.cookers.map { cookerResponseTransformer.transform(it) }
            }

    interface CookersServiceApi {
        @POST("client/cookers")
        fun loadCookersList(@Body cookersRequest: CookersDataRequest): Single<CookersDataResponse>

        @POST("map/cookers")
        fun loadCookersListWithoutAuth(@Body cookersRequest: CookersDataWithoutAuthRequest): Single<CookersDataResponse>

    }
}