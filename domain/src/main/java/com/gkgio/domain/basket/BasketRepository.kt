package com.gkgio.domain.basket

import io.reactivex.Completable
import io.reactivex.Single

interface BasketRepository {
    fun loadBasketCountAndSum(): BasketCountAndSum?
    fun updateBasketCountAndSum(basketCountAndSum: BasketCountAndSum): Single<BasketCountAndSum>
    fun loadBasketData(): Single<List<BasketData>>
    fun updateBasket(basketDataList: List<BasketData>): Single<List<BasketData>>
    fun clearBasket(): Completable
}