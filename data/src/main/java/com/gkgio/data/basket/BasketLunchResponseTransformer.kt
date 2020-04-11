package com.gkgio.data.basket

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.basket.BasketLunch
import com.gkgio.domain.basket.BasketMeal
import javax.inject.Inject

class BasketLunchResponseTransformer @Inject constructor(
    private val basketMealResponseTransformer: BasketMealResponseTransformer
) : BaseTransformer<BasketLunchResponse, BasketLunch> {

    override fun transform(data: BasketLunchResponse) = with(data) {
        BasketLunch(
            id,
            imageUrl,
            meals.map { basketMealResponseTransformer.transform(it) }
        )
    }
}