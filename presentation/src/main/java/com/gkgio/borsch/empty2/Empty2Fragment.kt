package com.gkgio.borsch.empty2

import android.os.Bundle
import android.view.View
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.createViewModel

class Empty2Fragment : BaseFragment<Empty2ViewModel>() {


    override fun getLayoutId(): Int = R.layout.fragment_empty1

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.empty2ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}