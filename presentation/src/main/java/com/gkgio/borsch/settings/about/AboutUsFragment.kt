package com.gkgio.borsch.settings.about

import android.os.Bundle
import android.view.View
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.createViewModel
import kotlinx.android.synthetic.main.fragment_about_us.*

class AboutUsFragment : BaseFragment<AboutUsViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_about_us

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.aboutUsViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        toolbar.setLeftIconClickListener {
            viewModel.onBackClick()
        }
    }
}