package com.gkgio.borsch.navigation

import android.content.Context
import androidx.fragment.app.Fragment
import com.gkgio.borsch.auth.InputPhoneFragment
import com.gkgio.borsch.auth.ValidatePhoneFragment
import com.gkgio.borsch.cookers.detail.CookerAddressUi
import com.gkgio.borsch.cookers.detail.CookerDetailFragment
import com.gkgio.borsch.location.FindAddressFragment
import com.gkgio.borsch.location.LocationFragment
import com.gkgio.borsch.main.MainFragment
import com.gkgio.borsch.onboarding.OnboardingFragment
import com.gkgio.borsch.profile.SettingsFragment
import com.gkgio.borsch.profile.about.AboutUsFragment
import com.gkgio.borsch.utils.IntentUtils
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object SettingsFragmentScreen : SupportAppScreen() {
        override fun getFragment() = SettingsFragment()
    }

    object MainFragmentScreen : SupportAppScreen() {
        override fun getFragment() = MainFragment()
    }

    class EmailScreen(
        private val email: String
    ) : SupportAppScreen() {
        override fun getActivityIntent(context: Context?) =
            IntentUtils.createEmailIntent(email)
    }

    object AboutUsFragmentScreen : SupportAppScreen() {
        override fun getFragment() = AboutUsFragment()
    }

    object OnboardingFragmentScreen : SupportAppScreen() {
        override fun getFragment() = OnboardingFragment()
    }

    object InputPhoneFragmentScreen : SupportAppScreen() {
        override fun getFragment() = InputPhoneFragment()
    }

    class ValidatePhoneFragmentScreen(private val phone: String) : SupportAppScreen() {
        override fun getFragment() = ValidatePhoneFragment.newInstance(phone)
    }

    class LocationFragmentScreen(
        private val isOpenFromOnboarding: Boolean = false
    ) : SupportAppScreen() {
        override fun getFragment() = LocationFragment.newInstance(isOpenFromOnboarding)
    }

    class FindAddressFragmentScreen(
        private val isOpenFromOnboarding: Boolean
    ) : SupportAppScreen() {
        override fun getFragment() = FindAddressFragment.newInstance(isOpenFromOnboarding)
    }

    class CookerDetailFragmentScreen(
        private val cookerId: String,
        private val foodId: String? = null,
        private val type: Int? = null,
        private val cookerAddressUi: CookerAddressUi? = null
    ) : SupportAppScreen() {
        override fun getFragment() =
            CookerDetailFragment.newInstance(cookerId, foodId, type, cookerAddressUi)
    }

    class MarketScreen(
        private val packageName: String
    ) : SupportAppScreen() {
        override fun getActivityIntent(context: Context?) =
            IntentUtils.createMarketIntent(packageName)
    }
}