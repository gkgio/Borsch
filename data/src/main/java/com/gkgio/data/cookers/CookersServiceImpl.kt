package com.gkgio.data.cookers

import com.gkgio.data.cookers.detail.CookerDetailDataResponse
import com.gkgio.data.cookers.detail.CookerDetailTransformer
import com.gkgio.domain.cookers.Cooker
import com.gkgio.domain.cookers.CookersRequest
import com.gkgio.domain.cookers.CookersService
import com.gkgio.domain.cookers.CookersWithoutAuthRequest
import com.gkgio.domain.cookers.detail.CookerDetail
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Inject

class CookersServiceImpl @Inject constructor(
    private val cookersServiceApi: CookersServiceApi,
    private val cookerResponseTransformer: CookerResponseTransformer,
    private val cookersDataRequestTransformer: CookersDataRequestTransformer,
    private val cookersDataWithoutAuthRequestTransformer: CookersDataWithoutAuthRequestTransformer,
    private val cookerDetailTransformer: CookerDetailTransformer
) : CookersService {

    override fun loadCookersList(cookersRequest: CookersRequest): Single<List<Cooker>> =
        cookersServiceApi.loadCookersList(
            cookersDataRequestTransformer.transform(
                cookersRequest
            )
        ).map { cookersData ->
            cookersData.cookers.map { cookerResponseTransformer.transform(it) }
        }

    override fun loadCookersListWithoutAuth(cookersRequest: CookersWithoutAuthRequest): Single<List<Cooker>> =
        cookersServiceApi.loadCookersListWithoutAuth(
            cookersDataWithoutAuthRequestTransformer.transform(
                cookersRequest
            )
        ).map { cookersData ->
            cookersData.cookers.map { cookerResponseTransformer.transform(it) }
        }

    override fun loadCookerDetail(cookerId: String): Single<CookerDetail> =
        cookersServiceApi.loadCookerDetail(cookerId)
            .map { cookerDetailTransformer.transform(it.cooker) }

    override fun loadCookerDetailWithoutAuth(cookerId: String): Single<CookerDetail> =
        cookersServiceApi.loadCookerDetailWithoutAuth(cookerId)
            .map { cookerDetailTransformer.transform(it.cooker) }

    interface CookersServiceApi {
        @POST("client/cookers")
        fun loadCookersList(@Body cookersRequest: CookersDataRequest): Single<CookersDataResponse>

        @POST("map/cookers")
        fun loadCookersListWithoutAuth(@Body cookersRequest: CookersDataWithoutAuthRequest): Single<CookersDataResponse>

        @GET("client/cookers/{cookerId}")
        fun loadCookerDetail(@Path("cookerId") cookerId: String): Single<CookerDetailDataResponse>

        @GET("public/cookers/{cookerId}")
        fun loadCookerDetailWithoutAuth(@Path("cookerId") cookerId: String): Single<CookerDetailDataResponse>
    }
}