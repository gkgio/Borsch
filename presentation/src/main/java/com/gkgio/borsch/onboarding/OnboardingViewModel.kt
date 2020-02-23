package com.gkgio.borsch.onboarding

import androidx.lifecycle.MutableLiveData
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.nonNullValue
import com.gkgio.borsch.navigation.Screens
import ru.terrakok.cicerone.Router
import java.text.FieldPosition
import javax.inject.Inject

class OnboardingViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository
) : BaseViewModel() {

}