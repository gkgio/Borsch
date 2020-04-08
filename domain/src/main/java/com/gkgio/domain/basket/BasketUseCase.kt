package com.gkgio.domain.basket

import io.reactivex.Completable
import io.reactivex.Single
import java.math.BigDecimal
import io.reactivex.functions.BiFunction
import javax.inject.Inject

interface BasketUseCase {
    fun addToBasket(
        id: String,
        name: String,
        price: BigDecimal,
        cookerId: String,
        count: Int = 1
    ): Single<BasketCountAndSum>

    fun removeFromBasket(id: String, cookerId: String): Completable
    fun loadBasketItem(id: String): Single<BasketData>
    fun loadBasketData(): Single<List<BasketData>>
    fun updateBasketItemCount(
        id: String,
        count: Int,
        price: BigDecimal,
        cookerId: String,
        isPlusOne: Boolean = false
    ): Single<List<BasketData>>

    fun clearBasket(): Completable
}

class BasketUseCaseImpl @Inject constructor(
    private val basketRepository: BasketRepository
) : BasketUseCase {

    override fun addToBasket(
        id: String,
        name: String,
        price: BigDecimal,
        cookerId: String,
        count: Int
    ): Single<BasketCountAndSum> =
        basketRepository
            .loadBasketData()
            .flatMap { basketDataList ->
                val mutableBasketDataList = basketDataList.toMutableList()
                mutableBasketDataList.add(
                    BasketData(
                        name = name,
                        id = id,
                        price = price,
                        count = count
                    )
                )
                var sum: BigDecimal = BigDecimal.ZERO
                mutableBasketDataList.forEach {
                    sum += it.price
                }
                val basketCountAndSum = BasketCountAndSum(mutableBasketDataList.size, sum, cookerId)

                Single.zip(
                    basketRepository.updateBasket(mutableBasketDataList),
                    basketRepository.updateBasketCountAndSum(basketCountAndSum),
                    BiFunction<List<BasketData>, BasketCountAndSum, BasketCountAndSum> { _, _ ->
                        basketCountAndSum
                    })
            }

    override fun loadBasketItem(id: String): Single<BasketData> =
        basketRepository.loadBasketData().flatMap {
            Single.fromCallable {
                it.find { it.id == id }
            }
        }

    override fun removeFromBasket(id: String, cookerId: String) =
        basketRepository
            .loadBasketData()
            .flatMapCompletable { basketDataList ->
                val mutableBasketDataList = basketDataList.toMutableList()
                val updateItem = mutableBasketDataList.find { it.id == id }
                if (updateItem != null) {
                    mutableBasketDataList.remove(updateItem)
                    var sum: BigDecimal = BigDecimal.ZERO
                    mutableBasketDataList.forEach {
                        sum += it.price
                    }
                    val basketCountAndSum =
                        BasketCountAndSum(mutableBasketDataList.size, sum, cookerId)

                    Single.zip(
                        basketRepository.updateBasket(mutableBasketDataList),
                        basketRepository.updateBasketCountAndSum(basketCountAndSum),
                        BiFunction<List<BasketData>, BasketCountAndSum, List<BasketData>> { _, _ ->
                            basketDataList
                        }).flatMapCompletable {
                        Completable.complete()
                    }
                } else {
                    Completable.complete()
                }
            }

    override fun loadBasketData(): Single<List<BasketData>> =
        basketRepository.loadBasketData()

    override fun updateBasketItemCount(
        id: String,
        count: Int,
        price: BigDecimal,
        cookerId: String,
        isPlusOne: Boolean
    ): Single<List<BasketData>> =
        basketRepository
            .loadBasketData()
            .flatMap { basketDataList ->
                val mutableBasketDataList = basketDataList.toMutableList()
                val updateItem = mutableBasketDataList.find { it.id == id }
                updateItem?.let {
                    updateItem.count = if (isPlusOne) updateItem.count + 1 else count
                    updateItem.price = if (isPlusOne) updateItem.price + price else price
                    val index = mutableBasketDataList.indexOf(updateItem)
                    mutableBasketDataList[index] = updateItem

                    var sum: BigDecimal = BigDecimal.ZERO
                    mutableBasketDataList.forEach {
                        sum += it.price
                    }
                    val basketCountAndSum =
                        BasketCountAndSum(mutableBasketDataList.size, sum, cookerId)

                    Single.zip(
                        basketRepository.updateBasket(mutableBasketDataList),
                        basketRepository.updateBasketCountAndSum(basketCountAndSum),
                        BiFunction<List<BasketData>, BasketCountAndSum, List<BasketData>> { basketDataList, _ ->
                            basketDataList
                        }).flatMap {
                        Single.fromCallable { it }
                    }
                }
            }

    override fun clearBasket(): Completable =
        basketRepository.clearBasket()
}