package com.gkgio.borsch.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gkgio.borsch.empty2.Empty2Fragment
import com.gkgio.borsch.emty1.Empty1Fragment
import com.gkgio.borsch.settings.SettingsFragment

class MainPagerAdapter(
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        const val PAGE_COUNT = 3
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Empty1Fragment()
            1 -> Empty2Fragment()
            2 -> SettingsFragment()
            else -> throw IllegalArgumentException("Unsupported tab")
        }
    }

    override fun getCount(): Int = PAGE_COUNT
}