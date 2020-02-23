package com.gkgio.borsch.emty1

import android.os.Bundle
import android.view.View
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.*


class Empty1Fragment : BaseFragment<Empty1ViewModel>() {

    companion object {
        val TAG = Empty1Fragment::class.java.simpleName
    }

    override fun getLayoutId(): Int = R.layout.fragment_empty2

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.empty1ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}