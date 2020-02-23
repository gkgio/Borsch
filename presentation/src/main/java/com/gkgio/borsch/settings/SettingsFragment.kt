package com.gkgio.borsch.settings

import android.os.Bundle
import android.view.View
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.createViewModel


class SettingsFragment : BaseFragment<SettingsViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_settings

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.settingsViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}