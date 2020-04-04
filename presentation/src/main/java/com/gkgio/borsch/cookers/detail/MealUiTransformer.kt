package com.gkgio.borsch.cookers.detail

import com.gkgio.borsch.utils.PriceFormatter
import com.gkgio.borsch.utils.WeightFormatter
import com.gkgio.data.BaseTransformer
import com.gkgio.domain.cookers.Meal
import javax.inject.Inject

class MealUiTransformer @Inject constructor(
    private val priceFormatter: PriceFormatter,
    private val weightFormatter: WeightFormatter
) : BaseTransformer<Meal, MealUi> {

    override fun transform(data: Meal) = with(data) {
        MealUi(
            available,
            calories,
            cookTime,
            description,
            id,
            imageUrl,
            ingredients,
            name,
            portions,
            priceFormatter.format(price),
            tags,
            type,
            weightFormatter.formatToOnePotion(weight)
        )
    }
}