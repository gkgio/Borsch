package com.gkgio.data.basket

import com.gkgio.domain.basket.BasketOrderRequest
import com.gkgio.domain.basket.BasketService
import com.gkgio.domain.basket.OrderData
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Inject

class BasketServiceImpl @Inject constructor(
    private val basketOrderDataRequestTransformer: BasketOrderDataRequestTransformer,
    private val basketServiceApi: BasketServiceApi,
    private val orderDataResponseTransformer: OrderDataResponseTransformer
) : BasketService {
    override fun createOrder(
        basketOrderRequest: BasketOrderRequest,
        cookerId: String
    ): Single<OrderData> =
        basketServiceApi.createBasketOrder(
            basketOrderDataRequestTransformer.transform(
                basketOrderRequest
            ),
            cookerId
        ).map { orderData -> orderDataResponseTransformer.transform(orderData.order) }


    override fun getBasketOrder(): Single<List<OrderData>> =
        basketServiceApi.getBasketOrder()
            .map { orderData ->
                orderData.orders.map {
                    orderDataResponseTransformer.transform(
                        it
                    )
                }
            }

    interface BasketServiceApi {
        @POST("client/orders/cookers/{cookerId}")
        fun createBasketOrder(
            @Body basketOrderDataRequest: BasketOrderDataRequest,
            @Path("cookerId") cookerId: String
        ): Single<OrderDataObjectResponse>

        @GET("client/orders")
        fun getBasketOrder(): Single<OrderDataListObjectResponse>
    }
}