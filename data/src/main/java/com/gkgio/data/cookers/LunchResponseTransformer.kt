package com.gkgio.data.cookers

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.cookers.CookersRequest
import com.gkgio.domain.cookers.Lunch
import java.math.BigDecimal
import javax.inject.Inject

class LunchResponseTransformer @Inject constructor(
    private val mealResponseTransformer: MealResponseTransformer
) : BaseTransformer<LunchResponse, Lunch> {

    override fun transform(data: LunchResponse) = with(data) {
        Lunch(
            available,
            availableTimeFrom,
            availableTimeTo,
            discountPercent,
            id,
            imageUrl,
            meals.map { mealResponseTransformer.transform(it) },
            name,
            BigDecimal(price),
            calories,
            weight,
            portions
        )
    }
}