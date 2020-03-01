package com.gkgio.borsch.cookers

import android.os.Bundle
import android.view.View
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.*


class CookersFragment : BaseFragment<CookersViewModel>() {

    companion object {
        val TAG = CookersFragment::class.java.simpleName
    }

    override fun getLayoutId(): Int = R.layout.fragment_favorites

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.empty1ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}