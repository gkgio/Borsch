package com.gkgio.data.cookers

import com.gkgio.domain.cookers.Cooker
import com.gkgio.domain.cookers.CookersRequest
import com.gkgio.domain.cookers.CookersService
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject

class CookersServiceImpl @Inject constructor(
    private val cookersServiceApi: CookersServiceApi,
    private val cookerResponseTransformer: CookerResponseTransformer,
    private val cookersDataRequestTransformer: CookersDataRequestTransformer
) : CookersService {

    override fun loadCookersList(cookersRequest: CookersRequest): Single<List<Cooker>> =
        cookersServiceApi.loadCookersList(cookersDataRequestTransformer.transform(cookersRequest))
            .map { cookersData ->
                cookersData.cookers.map { cookerResponseTransformer.transform(it) }
            }

    interface CookersServiceApi {
        @POST("client/cookers")
        fun loadCookersList(@Body cookersRequest: CookersDataRequest): Single<CookersDataResponse>
    }
}