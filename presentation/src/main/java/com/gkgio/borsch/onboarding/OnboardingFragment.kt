package com.gkgio.borsch.onboarding

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.createViewModel
import com.gkgio.borsch.ext.setDebounceOnClickListener
import kotlinx.android.synthetic.main.fragment_onboarding.*


class OnboardingFragment : BaseFragment<OnboardingViewModel>() {

    private val pagerAdapter = OnboardingPagerAdapter()

    override fun getLayoutId(): Int = R.layout.fragment_onboarding

    override fun provideViewModel() = createViewModel {
       AppInjector.appComponent.onboardingViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dotsIndicator.setCount(pagerAdapter.count)

        with(onboardingViewPager) {
            adapter = pagerAdapter
            addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    dotsIndicator.setIndicatorItem(position)
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    if (position == pagerAdapter.count - 1) {
                        onboardingGo.setDebounceOnClickListener {
                            viewModel.onGoToSecondPageClicked()
                        }
                    } else {
                        onboardingGo.setDebounceOnClickListener {
                            onboardingViewPager.currentItem = onboardingViewPager.currentItem + 1
                        }
                    }
                }
            })
        }
    }
}