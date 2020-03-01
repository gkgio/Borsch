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
        private const val PAGE_COOKERS = 0
        private const val PAGE_BASKET = 1
        private const val PAGE_FAVORITES = 2
        private const val PAGE_PROFILE = 3
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
                R.id.tab_cookers -> {
                    mainViewPager.setCurrentItem(0, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab_basket -> {
                    mainViewPager.setCurrentItem(0, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab_favorites -> {
                    mainViewPager.setCurrentItem(1, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab_profile -> {
                    mainViewPager.setCurrentItem(2, false)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

    }

    override fun switchToCookersTab() {
        bottomNavigation.selectedItemId = R.id.tab_cookers
        mainViewPager.setCurrentItem(PAGE_COOKERS, false)
    }

    override fun switchToBasketTab() {
        bottomNavigation.selectedItemId = R.id.tab_basket
        mainViewPager.setCurrentItem(PAGE_BASKET, false)
    }

    override fun switchToFavoritesTab() {
        bottomNavigation.selectedItemId = R.id.tab_favorites
        mainViewPager.setCurrentItem(PAGE_FAVORITES, false)
    }

    override fun switchToProfileTab() {
        bottomNavigation.selectedItemId = R.id.tab_profile
        mainViewPager.setCurrentItem(PAGE_PROFILE, false)
    }
}

interface BottomBarTabsSwitcher {
    fun switchToCookersTab()
    fun switchToBasketTab()
    fun switchToFavoritesTab()
    fun switchToProfileTab()
}