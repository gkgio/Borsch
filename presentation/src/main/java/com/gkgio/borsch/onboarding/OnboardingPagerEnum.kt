package com.gkgio.borsch.onboarding

import android.os.Parcelable
import androidx.annotation.StringRes
import com.gkgio.borsch.R
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class OnboardingPagerEnum(
    @StringRes val titleResId: Int,
    @StringRes val labelResId: Int
) : Parcelable {
    PAGE_1(R.string.onboarding_title_1, R.string.onboarding_label_1),
    PAGE_2(R.string.onboarding_title_2, R.string.onboarding_label_2),
    PAGE_3(R.string.onboarding_title_3, R.string.onboarding_label_3)
}
