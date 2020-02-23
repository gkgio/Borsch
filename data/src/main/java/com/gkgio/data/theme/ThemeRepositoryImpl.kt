package com.gkgio.data.theme

import android.content.SharedPreferences
import android.provider.Settings.Global.putString
import androidx.core.content.edit
import com.gkgio.domain.theme.ThemeRepository
import com.gkgio.domain.theme.ThemeState
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(
    private val prefs: SharedPreferences
) : ThemeRepository {

    private companion object {
        private const val KEY_CURRENT_THEME = "current_theme"
    }

    override fun loadTheme() =
        prefs.getString(KEY_CURRENT_THEME, ThemeState.THEME_DARK.value)
            ?: ThemeState.THEME_DARK.value

    override fun saveTheme(themeState: String) {
        prefs.edit {
            putString(KEY_CURRENT_THEME, themeState)
        }
    }
}