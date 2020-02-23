package com.gkgio.data.analytics

import android.os.Bundle
import com.gkgio.domain.analytics.AnalyticsRepository
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class AnalyticsRepositoryImpl @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsRepository {

    override fun sendEventHoroscopePeriodClick(date: String) {
        val bundle = Bundle().apply {
            putString("screen_name", "horoscope")
            putString("event_value", date)
        }
        firebaseAnalytics.logEvent("granulation", bundle)
    }

    override fun zodiakNameSuccessChanged(zodiak: String) {
        val bundle = Bundle().apply {
            putString("screen_name", "horoscope")
            putString("event_value", zodiak)
        }
        firebaseAnalytics.logEvent("scroll", bundle)
    }

    override fun zodiakTypeSuccessCahnged(type: String) {
        val bundle = Bundle().apply {
            putString("screen_name", "horoscope")
            putString("event_value", type)
        }
        firebaseAnalytics.logEvent("type", bundle)
    }

    override fun dreamStartSearch() {
        val bundle = Bundle().apply {
            putString("screen_name", "dream")
        }
        firebaseAnalytics.logEvent("search", bundle)
    }

    override fun dreamClickListItem(idWord: Long) {
        val bundle = Bundle().apply {
            putString("screen_name", "dream")
            putLong("event_value", idWord)
        }
        firebaseAnalytics.logEvent("choose", bundle)
    }

    override fun dreamOpenDetail(name: String, category: String) {
        val bundle = Bundle().apply {
            putString("screen_name", "dream")
            putString("event_value", "$name $category")
        }
        firebaseAnalytics.logEvent("roll", bundle)
    }

    override fun settingsClickWriteUs() {
        val bundle = Bundle().apply {
            putString("screen_name", "settings")
            putString("event_value", "write_us")
        }
        firebaseAnalytics.logEvent("configure", bundle)
    }

    override fun settingsChangeSign(name: String) {
        val bundle = Bundle().apply {
            putString("screen_name", "settings")
            putString("event_value", name)
        }
        firebaseAnalytics.logEvent("change_sign", bundle)
    }

    override fun onboardingSelectZodiak(name: String) {
        val bundle = Bundle().apply {
            putString("screen_name", "onboarding")
            putString("event_value", name)
        }
        firebaseAnalytics.logEvent("onboarding", bundle)
    }

}