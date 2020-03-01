package com.gkgio.borsch.onboarding

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.gkgio.borsch.R
import com.gkgio.borsch.ext.getViewByLayoutId
import kotlinx.android.synthetic.main.view_onboarding_page.view.*

class OnboardingPagerAdapter : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any =
        with(container.getViewByLayoutId(R.layout.view_onboarding_page)) {
            with(OnboardingPagerEnum.values()[position]) {
                onboardingPageTitle.text = context.getString(titleResId)
                onboardPageLabel.text = context.getString(labelResId)
            }
            container.addView(this)
            return this
        }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.parent?.let {
            container.removeView(`object` as View?)
        }
    }

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun getCount() = OnboardingPagerEnum.values().size
}