package com.gkgio.domain.basket

import io.reactivex.Completable
import io.reactivex.Single


interface BasketService {
    fun createOrder(basketOrderRequest: BasketOrderRequest, cookerId: String): Single<OrderData>
    fun getBasketOrder(): Single<List<OrderData>>
    fun getBasketOrderDetail(id: String): Single<OrderDetailData>
}