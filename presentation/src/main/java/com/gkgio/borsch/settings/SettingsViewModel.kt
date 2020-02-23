package com.gkgio.borsch.settings

import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.borsch.base.BaseViewModel
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository
) : BaseViewModel() {

}