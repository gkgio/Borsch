package com.gkgio.borsch.emty1

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.domain.analytics.AnalyticsRepository
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

class Empty1ViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository
) : BaseViewModel() {


}