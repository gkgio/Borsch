package com.gkgio.data.cookers

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.cookers.Cooker
import javax.inject.Inject

class CookerResponseTransformer @Inject constructor(
    private val mealResponseTransformer: MealResponseTransformer,
    private val lunchResponseTransformer: LunchResponseTransformer
) : BaseTransformer<CookerResponse, Cooker> {

    override fun transform(data: CookerResponse) = with(data) {
        Cooker(
            banned,
            certified,
            commission,
            id,
            meals.map { mealResponseTransformer.transform(it) },
            lunches?.map { lunchResponseTransformer.transform(it) },
            name,
            paid,
            phone,
            rating,
            suspended,
            verified,
            avatarUrl,
            delivery,
            countryTags,
            description
        )
    }
}