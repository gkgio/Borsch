package com.gkgio.borsch.main

import android.os.Bundle
import android.view.View
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.createViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<MainViewModel>(), BottomBarTabsSwitcher {

    private companion object {
        private const val PAGE_CACHE_SIZE = 4
        private const val PAGE_DREAM_BOOK = 0
        private const val PAGE_HOROSCOPE = 1
        private const val PAGE_SETTINGS = 2
    }

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.mainViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewPager.adapter = MainPagerAdapter(childFragmentManager)
        mainViewPager.offscreenPageLimit = PAGE_CACHE_SIZE


        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.tab_empty1 -> {
                    mainViewPager.setCurrentItem(0, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab_empty2 -> {
                    mainViewPager.setCurrentItem(1, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab_settings -> {
                    mainViewPager.setCurrentItem(2, false)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

    }

    override fun switchToDreamBookTab() {
        bottomNavigation.selectedItemId = R.id.tab_empty1
        mainViewPager.setCurrentItem(PAGE_DREAM_BOOK, false)
    }

    override fun switchToHoroscopeTab() {
        bottomNavigation.selectedItemId = R.id.tab_empty2
        mainViewPager.setCurrentItem(PAGE_HOROSCOPE, false)
    }

    override fun switchToSettingsTab() {
        bottomNavigation.selectedItemId = R.id.tab_settings
        mainViewPager.setCurrentItem(PAGE_SETTINGS, false)
    }
}

interface BottomBarTabsSwitcher {
    fun switchToDreamBookTab()
    fun switchToHoroscopeTab()
    fun switchToSettingsTab()
}