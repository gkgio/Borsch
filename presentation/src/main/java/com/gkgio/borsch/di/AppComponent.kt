package com.gkgio.borsch.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.domain.auth.AuthRepository
import com.gkgio.domain.theme.ThemeRepository
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.base.bottomsheet.BaseBottomSheetDialog
import com.gkgio.borsch.empty2.Empty2ViewModel
import com.gkgio.borsch.emty1.Empty1ViewModel
import com.gkgio.borsch.main.LaunchActivity
import com.gkgio.borsch.main.LaunchViewModel
import com.gkgio.borsch.main.MainViewModel
import com.gkgio.borsch.onboarding.OnboardingViewModel
import com.gkgio.borsch.settings.SettingsViewModel
import com.gkgio.borsch.settings.about.AboutUsViewModel
import com.squareup.moshi.Moshi
import dagger.Component
import retrofit2.Retrofit
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NavigationModule::class,
        AppModule::class,
        AppModule::class,
        AuthModule::class,
        AnalyticsModule::class,
        ThemeModule::class

    ]
)
interface AppComponent {
    fun inject(app: Application)

    fun inject(launchActivity: LaunchActivity)
    fun inject(baseFragment: BaseFragment<BaseViewModel>)
    fun inject(baseBottomSheetDialog: BaseBottomSheetDialog)

    val launchViewModel: LaunchViewModel
    val mainViewModel: MainViewModel
    val empty2ViewModel: Empty2ViewModel
    val empty1ViewModel: Empty1ViewModel
    val settingsViewModel: SettingsViewModel
    val onboardingViewModel: OnboardingViewModel
    val aboutUsViewModel: AboutUsViewModel

    val context: Context
    val moshi: Moshi
    val retrofit: Retrofit
    val prefs: SharedPreferences

    // Repositories
    val analyticsRepository: AnalyticsRepository
    val authRepository: AuthRepository
    val themeRepository: ThemeRepository

    //event

    //Cicerone
    val router: Router
    val navigatorHolder: NavigatorHolder
}