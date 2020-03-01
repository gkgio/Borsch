package com.gkgio.borsch.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gkgio.borsch.favorites.FavoritesFragment
import com.gkgio.borsch.cookers.CookersFragment
import com.gkgio.borsch.profile.SettingsFragment

class MainPagerAdapter(
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        const val PAGE_COUNT = 3
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> CookersFragment()
            1 -> FavoritesFragment()
            2 -> SettingsFragment()
            else -> throw IllegalArgumentException("Unsupported tab")
        }
    }

    override fun getCount(): Int = PAGE_COUNT
}