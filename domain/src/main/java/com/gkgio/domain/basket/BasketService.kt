package com.gkgio.domain.basket

import io.reactivex.Completable


interface BasketService {
    fun createOrder(basketOrderRequest: BasketOrderRequest, cookerId: String): Completable
}