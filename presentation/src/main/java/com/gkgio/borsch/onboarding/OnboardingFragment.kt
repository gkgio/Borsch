package com.gkgio.borsch.onboarding

import android.os.Bundle
import android.view.View
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.createViewModel


class OnboardingFragment : BaseFragment<OnboardingViewModel>() {

    companion object {
        private const val VIBRATION_DURATION = 1L
    }

    override fun getLayoutId(): Int = R.layout.fragment_onboarding

    override fun provideViewModel() = createViewModel {
       AppInjector.appComponent.onboardingViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}