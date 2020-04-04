package com.gkgio.borsch.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.gkgio.borsch.auth.InputPhoneViewModel
import com.gkgio.borsch.auth.ValidatePhoneViewModel
import com.gkgio.domain.analytics.AnalyticsRepository
import com.gkgio.domain.auth.AuthRepository
import com.gkgio.domain.theme.ThemeRepository
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.base.bottomsheet.BaseBottomSheetDialog
import com.gkgio.borsch.basket.BasketViewModel
import com.gkgio.borsch.favorites.FavoritesViewModel
import com.gkgio.borsch.cookers.CookersViewModel
import com.gkgio.borsch.cookers.detail.CookerDetailViewModel
import com.gkgio.borsch.cookers.detail.information.CookerInformationViewModel
import com.gkgio.borsch.cookers.detail.meals.CookerMealViewModel
import com.gkgio.borsch.location.FindAddressViewModel
import com.gkgio.borsch.location.LocationViewModel
import com.gkgio.borsch.location.saved.SavedAddressesViewModel
import com.gkgio.borsch.main.LaunchActivity
import com.gkgio.borsch.main.LaunchViewModel
import com.gkgio.borsch.main.MainViewModel
import com.gkgio.borsch.onboarding.OnboardingViewModel
import com.gkgio.borsch.profile.SettingsViewModel
import com.gkgio.borsch.profile.about.AboutUsViewModel
import com.gkgio.borsch.utils.events.AddressChangedEvent
import com.gkgio.domain.errorreporter.ErrorReporter
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
        ThemeModule::class,
        OnboardingModule::class,
        CookersModule::class,
        LocationModule::class
    ]
)
interface AppComponent {
    fun inject(app: Application)

    fun inject(launchActivity: LaunchActivity)
    fun inject(baseFragment: BaseFragment<BaseViewModel>)
    fun inject(baseBottomSheetDialog: BaseBottomSheetDialog)

    val launchViewModel: LaunchViewModel
    val mainViewModel: MainViewModel
    val empty2ViewModel: FavoritesViewModel
    val cookersViewModel: CookersViewModel
    val settingsViewModel: SettingsViewModel
    val onboardingViewModel: OnboardingViewModel
    val aboutUsViewModel: AboutUsViewModel
    val inputPhoneViewModel: InputPhoneViewModel
    val validatePhoneViewModel: ValidatePhoneViewModel
    val basketViewModel: BasketViewModel
    val locationViewModel: LocationViewModel
    val findAddressViewModel: FindAddressViewModel
    val savedAddressesViewModel: SavedAddressesViewModel
    val cookerDetailViewModel: CookerDetailViewModel
    val cookerMealViewModel: CookerMealViewModel
    val cookerInformationViewModel: CookerInformationViewModel

    val context: Context
    val moshi: Moshi
    val retrofit: Retrofit
    val prefs: SharedPreferences

    // Repositories
    val analyticsRepository: AnalyticsRepository
    val authRepository: AuthRepository
    val themeRepository: ThemeRepository

    val errorReporter: ErrorReporter

    //event
    val addressChangedEvent: AddressChangedEvent

    //Cicerone
    val router: Router
    val navigatorHolder: NavigatorHolder
}