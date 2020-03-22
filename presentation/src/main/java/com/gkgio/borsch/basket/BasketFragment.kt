package com.gkgio.borsch.basket

import android.os.Bundle
import android.view.View
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.cookers.CookersFragment
import com.gkgio.borsch.cookers.CookersViewModel
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.*


class BasketFragment : BaseFragment<BasketViewModel>() {

    companion object {
        val TAG = CookersFragment::class.java.simpleName
    }

    override fun getLayoutId(): Int = R.layout.fragment_basket

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.basketViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}