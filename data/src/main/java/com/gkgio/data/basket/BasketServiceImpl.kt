package com.gkgio.data.basket

import com.gkgio.domain.basket.BasketOrderRequest
import com.gkgio.domain.basket.BasketService
import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Inject

class BasketServiceImpl @Inject constructor(
    private val basketOrderDataRequestTransformer: BasketOrderDataRequestTransformer,
    private val basketServiceApi: BasketServiceApi
) : BasketService {
    override fun createOrder(
        basketOrderRequest: BasketOrderRequest,
        cookerId: String
    ): Completable =
        basketServiceApi.createBasketOrder(
            basketOrderDataRequestTransformer.transform(
                basketOrderRequest
            ),
            cookerId
        )


    interface BasketServiceApi {
        @POST("client/orders/cookers/{cookerId}")
        fun createBasketOrder(
            @Body basketOrderDataRequest: BasketOrderDataRequest,
            @Path("cookerId") cookerId: String
        ): Completable
    }
}