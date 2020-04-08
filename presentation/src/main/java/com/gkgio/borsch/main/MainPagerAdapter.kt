package com.gkgio.borsch.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gkgio.borsch.basket.BasketFragment
import com.gkgio.borsch.orders.OrdersFragment
import com.gkgio.borsch.cookers.CookersFragment
import com.gkgio.borsch.profile.SettingsFragment

class MainPagerAdapter(
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        const val PAGE_COUNT = 4
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> CookersFragment()
            1 -> BasketFragment()
            2 -> OrdersFragment()
            3 -> SettingsFragment()
            else -> throw IllegalArgumentException("Unsupported tab")
        }
    }

    override fun getCount(): Int = PAGE_COUNT
}