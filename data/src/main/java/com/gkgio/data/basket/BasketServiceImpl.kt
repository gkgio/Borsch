package com.gkgio.data.basket

import com.gkgio.data.base.BaseService
import com.gkgio.data.exception.ServerExceptionTransformer
import com.gkgio.domain.basket.BasketOrderRequest
import com.gkgio.domain.basket.BasketService
import com.gkgio.domain.basket.OrderData
import com.gkgio.domain.basket.OrderDetailData
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
    private val orderDataResponseTransformer: OrderDataResponseTransformer,
    private val orderDetailDataResponseTransformer: OrderDetailDataResponseTransformer,
    serverExceptionTransformer: ServerExceptionTransformer
) : BaseService(serverExceptionTransformer), BasketService {

    override fun createOrder(
        basketOrderRequest: BasketOrderRequest,
        cookerId: String
    ): Single<OrderData> = executeRequest(
        basketServiceApi.createBasketOrder(
            basketOrderDataRequestTransformer.transform(
                basketOrderRequest
            ),
            cookerId
        ).map { orderData -> orderDataResponseTransformer.transform(orderData.order) }
    )

    override fun getBasketOrder(): Single<List<OrderData>> = executeRequest(
        basketServiceApi.getBasketOrder()
            .map { orderData ->
                orderData.orders.map {
                    orderDataResponseTransformer.transform(
                        it
                    )
                }
            }
    )

    override fun getBasketOrderDetail(id: String): Single<OrderDetailData> = executeRequest(
        basketServiceApi.getBasketOrderDetail(id)
            .map { orderDetailDataResponseTransformer.transform(it) }
    )

    override fun cancelOrder(id: String): Completable = executeRequest(
        basketServiceApi.cancelOrder(id)
            .flatMapCompletable {
                Completable.complete()
            }
    )

    interface BasketServiceApi {
        @POST("client/orders/cookers/{cookerId}")
        fun createBasketOrder(
            @Body basketOrderDataRequest: BasketOrderDataRequest,
            @Path("cookerId") cookerId: String
        ): Single<OrderDataObjectResponse>

        @GET("client/orders")
        fun getBasketOrder(): Single<OrderDataListObjectResponse>

        @GET("client/orders/{id}")
        fun getBasketOrderDetail(@Path("id") id: String): Single<OrderDetailDataResponse>

        @POST("client/orders/{id}/cancel")
        fun cancelOrder(@Path("id") id: String): Single<OrderCancelResponse>
    }
}