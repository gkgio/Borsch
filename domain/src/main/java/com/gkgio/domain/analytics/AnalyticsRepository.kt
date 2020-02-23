package com.gkgio.domain.analytics

interface AnalyticsRepository {
    fun sendEventHoroscopePeriodClick(date: String)
    fun zodiakNameSuccessChanged(zodiak: String)
    fun zodiakTypeSuccessCahnged(type: String)
    fun dreamStartSearch()
    fun dreamClickListItem(idWord: Long)
    fun dreamOpenDetail(name: String, category: String)
    fun settingsClickWriteUs()
    fun settingsChangeSign(name: String)
    fun onboardingSelectZodiak(name: String)
}
