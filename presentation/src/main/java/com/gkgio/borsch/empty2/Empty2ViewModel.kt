package com.gkgio.borsch.empty2

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.domain.analytics.AnalyticsRepository
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class Empty2ViewModel @Inject constructor(
    private val router: Router,
    private val analyticsRepository: AnalyticsRepository
) : BaseViewModel() {

}