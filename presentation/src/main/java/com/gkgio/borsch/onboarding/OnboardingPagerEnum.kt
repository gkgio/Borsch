package com.gkgio.borsch.onboarding

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.gkgio.borsch.R
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class OnboardingPagerEnum(
    @StringRes val labelResId: Int,
    @DrawableRes val imageResId: Int
) : Parcelable {
    PAGE_1(R.string.onboarding_label_1, R.drawable.onboarding_1),
    PAGE_2(R.string.onboarding_label_2, R.drawable.onboarding_2),
    PAGE_3(R.string.onboarding_label_3, R.drawable.onboarding_3),
    PAGE_4(R.string.onboarding_label_4, R.drawable.onboarding_4)
}
