package com.gkgio.borsch.cookers.detail

import android.content.Context
import com.gkgio.borsch.R
import com.gkgio.data.BaseTransformer
import com.gkgio.domain.cookers.Cooker
import javax.inject.Inject


class CookerUiTransformer @Inject constructor(
    private val lunchUiTransformer: LunchUiTransformer,
    private val mealUiTransformer: MealUiTransformer,
    private val context: Context
) : BaseTransformer<Cooker, CookerUi> {

    private companion object {
        private const val METRES_IN_KM = 1000f
    }

    override fun transform(data: Cooker) = with(data) {
        CookerUi(
            banned,
            certified,
            id,
            meals.map { mealUiTransformer.transform(it) },
            lunches?.map { lunchUiTransformer.transform(it) },
            name,
            phone,
            rating,
            suspended,
            verified,
            avatarUrl,
            delivery,
            countryTags,
            description ?: "",
            onDuty,
            distance?.let { transformDistance(it) }
        )
    }


    private fun transformDistance(distance: Int): String {
        val isKm = distance > METRES_IN_KM
        return context.getString(
            if (isKm) R.string.place_distance_km_format else R.string.place_distance_metres_format,
            if (isKm) distance / METRES_IN_KM else distance
        )
    }
}