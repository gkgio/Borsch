package com.gkgio.data.basket

import android.content.SharedPreferences
import com.gkgio.domain.basket.BasketCountAndSum
import com.gkgio.domain.basket.BasketData
import com.gkgio.domain.basket.BasketRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(
    private val prefs: SharedPreferences,
    private val moshi: Moshi,
    private val basketCountAndSumToRepositoryDataTransformer: BasketCountAndSumToRepositoryDataTransformer,
    private val basketCountAndSumFromRepositoryDataTransformer: BasketCountAndSumFromRepositoryDataTransformer,
    private val basketToRepositoryDataTransformer: BasketToRepositoryDataTransformer,
    private val basketFromRepositoryDataTransformer: BasketFromRepositoryDataTransformer
) : BasketRepository {

    private companion object {
        private const val KEY_BASKET_COUNT_AND_SUM = "basket_count_and_sum"
        private const val KEY_BASKET_DATA = "basket_data"
    }

    override fun loadBasketCountAndSum(): BasketCountAndSum? {
        val basketCountAndSumJsonString = prefs.getString(KEY_BASKET_COUNT_AND_SUM, null)
        basketCountAndSumJsonString?.let { basketCountAndSum ->
            val data = moshi.adapter(BasketCountAndSumRepositoryData::class.java)
                .fromJson(basketCountAndSum)
            data?.let {
                return basketCountAndSumFromRepositoryDataTransformer.transform(it)
            }
        }
        return null
    }


    override fun updateBasketCountAndSum(basketCountAndSum: BasketCountAndSum): Single<BasketCountAndSum> =
        Single.fromCallable {
            if (basketCountAndSum.count == 0) {
                prefs.edit().remove(KEY_BASKET_COUNT_AND_SUM).apply()
            } else {
                prefs.edit().putString(
                    KEY_BASKET_COUNT_AND_SUM,
                    moshi.adapter(BasketCountAndSumRepositoryData::class.java)
                        .toJson(
                            basketCountAndSumToRepositoryDataTransformer.transform(basketCountAndSum)
                        )
                ).apply()
            }
            basketCountAndSum
        }

    override fun loadBasketData(): Single<List<BasketData>> = Single.fromCallable {
        val type =
            Types.newParameterizedType(
                List::class.java,
                BasketRepositoryData::class.java
            )

        val basetJsonString = prefs.getString(KEY_BASKET_DATA, null)
        if (basetJsonString != null) {
            moshi.adapter<List<BasketRepositoryData>>(type)
                .fromJson(basetJsonString)!!
                .map {
                    basketFromRepositoryDataTransformer.transform(it)
                }
        } else {
            listOf()
        }
    }

    override fun updateBasket(basketDataList: List<BasketData>) = Single.fromCallable {
        val type =
            Types.newParameterizedType(
                List::class.java,
                BasketRepositoryData::class.java
            )

        val dataJsonString = moshi.adapter<List<BasketRepositoryData>>(type).toJson(
            basketDataList.map {
                basketToRepositoryDataTransformer.transform(it)
            }
        )
        prefs.edit().putString(KEY_BASKET_DATA, dataJsonString).apply()
        basketDataList
    }

    override fun clearBasket() = Completable.fromCallable {
        prefs.edit().remove(KEY_BASKET_COUNT_AND_SUM).apply()
        prefs.edit().remove(KEY_BASKET_DATA).apply()
    }
}