package com.gkgio.borsch.cookers.detail

import com.gkgio.borsch.utils.PriceFormatter
import com.gkgio.borsch.utils.WeightFormatter
import com.gkgio.data.BaseTransformer
import com.gkgio.domain.cookers.Lunch
import javax.inject.Inject

class LunchUiTransformer @Inject constructor(
    private val priceFormatter: PriceFormatter,
    private val weightFormatter: WeightFormatter,
    private val mealUiTransformer: MealUiTransformer
) : BaseTransformer<Lunch, LunchUi> {

    override fun transform(data: Lunch) = with(data) {
        LunchUi(
            available,
            availableTimeFrom,
            availableTimeTo,
            discountPercent,
            id,
            imageUrl,
            meals.map { mealUiTransformer.transform(it) },
            name,
            priceFormatter.format(price),
            calories,
            weight?.let { weightFormatter.formatToOnePotion(it) },
            portions,
            price
        )
    }
}