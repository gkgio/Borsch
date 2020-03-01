package com.gkgio.borsch

import androidx.multidex.MultiDexApplication
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.di.AppModule
import com.gkgio.borsch.di.DaggerAppComponent
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initDagger()
        initTimber()
        initRxErrorHandler()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initDagger() {
        val appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        AppInjector.appComponent = appComponent
    }

    private fun initRxErrorHandler() {
        RxJavaPlugins.setErrorHandler {
            Timber.e(it, "Rx error")
            AppInjector.appComponent.errorReporter.log(it)
            if (BuildConfig.DEBUG) {
                throw it
            }
        }
    }
}