package com.gkgio.borsch.navigation

import android.content.Context
import androidx.fragment.app.Fragment
import com.gkgio.borsch.auth.InputPhoneFragment
import com.gkgio.borsch.auth.ValidatePhoneFragment
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

    object LocationFragmentScreen : SupportAppScreen() {
        override fun getFragment() = LocationFragment()
    }

    class MarketScreen(
        private val packageName: String
    ) : SupportAppScreen() {
        override fun getActivityIntent(context: Context?) =
            IntentUtils.createMarketIntent(packageName)
    }
}