package com.gkgio.borsch.main

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.basket.BasketFragment
import com.gkgio.borsch.cookers.CookersFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.createViewModel
import com.gkgio.borsch.ext.observeValue
import com.gkgio.borsch.orders.OrdersFragment
import com.gkgio.borsch.profile.SettingsFragment
import com.gkgio.borsch.support.SupportFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<MainViewModel>(), BottomBarTabsSwitcher {

    private companion object {
        private const val PAGE_COOKERS = 0
        private const val PAGE_BASKET = 1
        private const val PAGE_ORDERS = 2
        private const val PAGE_SUPPORT = 3
        private const val PAGE_PROFILE = 4
    }

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.mainViewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(bottomNavigation) { view, insets ->
            view.isVisible = insets.systemWindowInsetBottom == 0
            insets
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.openFirstTab.observeValue(this) {
            childFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(
                    R.id.fragmentContainer,
                    getFragment(R.id.tab_cookers),
                    getFragmentTag(R.id.tab_cookers)
                )
                .commit()
        }

        bottomNavigation.setOnNavigationItemSelectedListener {
            val tag = getFragmentTag(it.itemId)
            val fragment = childFragmentManager.findFragmentByTag(tag) ?: getFragment(it.itemId)
            replaceFragment(fragment, tag)
            return@setOnNavigationItemSelectedListener true
        }

    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        childFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, fragment, tag)
            .commit()
    }

    private fun getFragment(itemId: Int) = when (itemId) {
        R.id.tab_cookers -> CookersFragment()
        R.id.tab_basket -> BasketFragment()
        R.id.tab_orders -> OrdersFragment()
        R.id.tab_support -> SupportFragment()
        R.id.tab_profile -> SettingsFragment()
        else -> throw IllegalArgumentException("Unsupported tab")
    }

    private fun getFragmentTag(itemId: Int) = when (itemId) {
        R.id.tab_cookers -> PAGE_COOKERS.toString()
        R.id.tab_basket -> PAGE_BASKET.toString()
        R.id.tab_orders -> PAGE_ORDERS.toString()
        R.id.tab_support -> PAGE_SUPPORT.toString()
        R.id.tab_profile -> PAGE_PROFILE.toString()
        else -> throw IllegalArgumentException("Unsupported tab")
    }

    override fun switchToCookersTab() {
        bottomNavigation.selectedItemId = R.id.tab_cookers
    }

    override fun switchToBasketTab() {
        bottomNavigation.selectedItemId = R.id.tab_basket
    }

    override fun switchToFavoritesTab() {
        bottomNavigation.selectedItemId = R.id.tab_orders
    }

    override fun switchToProfileTab() {
        bottomNavigation.selectedItemId = R.id.tab_profile
    }

    override fun switchToSupportTab() {
        bottomNavigation.selectedItemId = R.id.tab_support
    }
}

interface BottomBarTabsSwitcher {
    fun switchToCookersTab()
    fun switchToBasketTab()
    fun switchToFavoritesTab()
    fun switchToProfileTab()
    fun switchToSupportTab()
}