package com.gkgio.data.cookers

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.cookers.Meal
import javax.inject.Inject

class MealResponseTransformer @Inject constructor() : BaseTransformer<MealResponse, Meal> {

    override fun transform(data: MealResponse) = with(data) {
        Meal(
            available,
            calories,
            cookTime,
            description,
            id,
            imageUrl,
            ingredients,
            name,
            portions,
            price,
            tags,
            type,
            weight
        )
    }
}