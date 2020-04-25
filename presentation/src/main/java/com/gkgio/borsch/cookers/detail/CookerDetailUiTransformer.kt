package com.gkgio.borsch.cookers.detail

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.cookers.detail.CookerDetail
import java.text.DecimalFormat
import javax.inject.Inject

class CookerDetailUiTransformer @Inject constructor(
    private val lunchUiTransformer: LunchUiTransformer,
    private val mealUiTransformer: MealUiTransformer,
    private val cookerAddressUiTransformer: CookerAddressUiTransformer
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
            getRating(rating),
            suspended,
            verified,
            avatarUrl,
            delivery,
            countryTags,
            description ?: "",
            onDuty,
            cookerAddress?.let { cookerAddressUiTransformer.transform(it) }
        )
    }

    private fun getRating(rating: Double): String {
        if (rating < 0) return "мало оценок"
        val decimalFormat = DecimalFormat("###.#");
        return decimalFormat.format(rating)
    }
}