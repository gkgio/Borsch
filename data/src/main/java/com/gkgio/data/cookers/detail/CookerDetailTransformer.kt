package com.gkgio.data.cookers.detail

import com.gkgio.data.BaseTransformer
import com.gkgio.data.cookers.CookerResponse
import com.gkgio.data.cookers.LunchResponseTransformer
import com.gkgio.data.cookers.MealResponseTransformer
import com.gkgio.domain.cookers.Cooker
import com.gkgio.domain.cookers.detail.CookerDetail
import javax.inject.Inject

data class CookerDetailTransformer @Inject constructor(
    private val mealResponseTransformer: MealResponseTransformer,
    private val lunchResponseTransformer: LunchResponseTransformer,
    private val cookerAddressResponseTransformer: CookerAddressResponseTransformer
) : BaseTransformer<CookerDetailResponse, CookerDetail> {

    override fun transform(data: CookerDetailResponse) = with(data) {
        CookerDetail(
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
            description,
            onDuty,
            address?.let { cookerAddressResponseTransformer.transform(address) }
        )
    }
}