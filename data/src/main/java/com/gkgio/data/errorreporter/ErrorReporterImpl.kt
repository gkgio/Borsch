package com.gkgio.data.errorreporter

import com.gkgio.domain.errorreporter.ErrorReporter
import com.google.android.gms.common.util.CrashUtils
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.core.CrashlyticsCore
import javax.inject.Inject

class ErrorReporterImpl @Inject constructor() : ErrorReporter {

    override fun log(throwable: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(throwable)
    }
}