package com.gkgio.data.basket

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.basket.BasketData
import com.gkgio.domain.basket.BasketMeal
import javax.inject.Inject

class BasketMealResponseTransformer @Inject constructor() :
    BaseTransformer<BasketMealResponse, BasketMeal> {

    override fun transform(data: BasketMealResponse) = with(data) {
        BasketMeal(
            id,
            imageUrl,
            name
        )
    }
}