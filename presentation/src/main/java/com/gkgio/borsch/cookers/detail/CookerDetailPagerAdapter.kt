package com.gkgio.borsch.cookers.detail

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.gkgio.borsch.R
import com.gkgio.borsch.cookers.detail.information.CookerInformationFragment
import com.gkgio.borsch.cookers.detail.meals.CookerMealFragment

class CookerDetailPagerAdapter(
    private val cookerDetailUi: CookerDetailUi,
    private val context: Context,
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private companion object {
        private const val TABS_COUNT = 2
    }

    override fun getItem(position: Int) = when (position) {
        0 -> CookerMealFragment.newInstance(cookerDetailUi)
        else -> CookerInformationFragment.newInstance(cookerDetailUi.description)
    }

    override fun getPageTitle(position: Int) = when (position) {
        0 -> context.getString(R.string.cooker_page_meals)
        else -> context.getString(R.string.cooker_page_information)
    }

    override fun getCount() = TABS_COUNT
}