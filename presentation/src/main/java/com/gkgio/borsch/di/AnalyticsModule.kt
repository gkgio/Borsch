package com.gkgio.borsch.di

import android.content.Context
import com.gkgio.data.analytics.AnalyticsRepositoryImpl
import com.gkgio.domain.analytics.AnalyticsRepository
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [AnalyticsModule.BindsModule::class])
class AnalyticsModule {
    companion object {
        const val FIREBASE_API_KEY = "FIREBASE_API_KEY"
    }

    @Provides
    fun provideFirebaseAnalytics(app: Context): FirebaseAnalytics =
        FirebaseAnalytics.getInstance(app.applicationContext)

    @Module
    abstract inner class BindsModule {
        @Binds
        abstract fun provideAnalyticsRepository(arg: AnalyticsRepositoryImpl): AnalyticsRepository
    }
}