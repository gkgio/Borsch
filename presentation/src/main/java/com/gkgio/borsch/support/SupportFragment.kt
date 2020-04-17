package com.gkgio.borsch.support

import android.os.Bundle
import android.view.View
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.createViewModel

class SupportFragment : BaseFragment<SupportViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_orders

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.supportViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}