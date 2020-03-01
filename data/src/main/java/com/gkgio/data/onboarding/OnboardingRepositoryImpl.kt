package com.gkgio.data.onboarding

import android.content.SharedPreferences
import androidx.core.content.edit
import com.gkgio.domain.onboarding.OnboardingRepository
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val prefs: SharedPreferences
) : OnboardingRepository {

    private companion object {
        private const val KEY_ONBOARDING_VERSION = "onboarding_version_key"
        private const val KEY_IS_FIRST_START = "is_first_start"
    }

    override val isFirstStart: Boolean
        get() {
            val startInfo = prefs.getBoolean(KEY_IS_FIRST_START, true)
            if (startInfo) {
                prefs.edit().putBoolean(KEY_IS_FIRST_START, false).apply()
            }
            return startInfo
        }

    override fun saveVersion(version: Int) = prefs.edit {
        putInt(KEY_ONBOARDING_VERSION, version)
    }

    override fun getVersion() =
        prefs.getInt(KEY_ONBOARDING_VERSION, 0)
}