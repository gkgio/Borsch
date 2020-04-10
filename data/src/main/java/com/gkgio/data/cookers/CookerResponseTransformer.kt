package com.gkgio.data.cookers

import com.gkgio.data.BaseTransformer
import com.gkgio.data.cookers.detail.CookerAddressResponseTransformer
import com.gkgio.domain.cookers.Cooker
import javax.inject.Inject

class CookerResponseTransformer @Inject constructor(
    private val mealResponseTransformer: MealResponseTransformer,
    private val lunchResponseTransformer: LunchResponseTransformer,
    private val cookerAddressResponseTransformer: CookerAddressResponseTransformer
) : BaseTransformer<CookerResponse, Cooker> {

    override fun transform(data: CookerResponse) = with(data) {
        Cooker(
            banned,
            certified,
            id,
            meals.map { mealResponseTransformer.transform(it) },
            lunches?.map { lunchResponseTransformer.transform(it) },
            name,
            phone,
            rating,
            suspended,
            verified,
            avatarUrl,
            delivery,
            countryTags,
            description,
            onDuty,
            distance,
            address?.let { cookerAddressResponseTransformer.transform(address) }
        )
    }
}