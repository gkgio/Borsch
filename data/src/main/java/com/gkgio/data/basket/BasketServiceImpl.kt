package com.gkgio.data.basket

import com.gkgio.data.base.BaseService
import com.gkgio.data.exception.ServerExceptionTransformer
import com.gkgio.domain.basket.*
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
    private val orderDetailResponseTransformer: OrderDetailResponseTransformer,
    serverExceptionTransformer: ServerExceptionTransformer
) : BaseService(serverExceptionTransformer), BasketService {

    override fun createOrder(
        basketOrderRequest: BasketOrderRequest
    ): Single<OrderData> = executeRequest(
        basketServiceApi.createBasketOrder(
            basketOrderDataRequestTransformer.transform(
                basketOrderRequest
            )
        ).map { orderData -> orderDataResponseTransformer.transform(orderData) }
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

    override fun getBasketOrderDetail(id: String): Single<OrderDetail> = executeRequest(
        basketServiceApi.getBasketOrderDetail(id)
            .map { orderDetailResponseTransformer.transform(it.order) }
    )

    override fun cancelOrder(id: String): Completable = executeRequest(
        basketServiceApi.cancelOrder(id)
            .flatMapCompletable {
                Completable.complete()
            }
    )

    interface BasketServiceApi {
        @POST("client/orders")
        fun createBasketOrder(
            @Body basketOrderDataRequest: BasketOrderDataRequest
        ): Single<OrderDataResponse>

        @GET("client/orders")
        fun getBasketOrder(): Single<OrderDataListObjectResponse>

        @GET("client/orders/{id}")
        fun getBasketOrderDetail(@Path("id") id: String): Single<OrderDetailDataResponse>

        @POST("client/orders/cancel/{order_id}")
        fun cancelOrder(@Path("order_id") id: String): Single<OrderCancelResponse>
    }
}