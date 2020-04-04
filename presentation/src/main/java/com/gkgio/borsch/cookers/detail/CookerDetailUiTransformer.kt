package com.gkgio.borsch.cookers.detail

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.cookers.detail.CookerDetail
import javax.inject.Inject

class CookerDetailUiTransformer @Inject constructor(
    private val lunchUiTransformer: LunchUiTransformer,
    private val mealUiTransformer: MealUiTransformer
) : BaseTransformer<CookerDetail, CookerDetailUi> {

    override fun transform(data: CookerDetail) = with(data) {
        CookerDetailUi(
            banned,
            certified,
            commission,
            id,
            meals.map { mealUiTransformer.transform(it) },
            lunches?.map { lunchUiTransformer.transform(it) },
            name,
            paid,
            phone,
            rating,
            suspended,
            verified,
            avatarUrl,
            delivery,
            countryTags,
            description ?: "",
            onDuty
        )
    }
}