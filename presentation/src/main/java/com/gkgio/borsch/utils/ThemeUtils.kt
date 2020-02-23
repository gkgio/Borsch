package com.gkgio.borsch.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.gkgio.domain.theme.ThemeState
import com.gkgio.borsch.R

object ThemeUtils {

    fun applyTheme(themeState: String) {
        when (themeState) {
            ThemeState.THEME_LIGHT.value -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            ThemeState.THEME_DARK.value -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            ThemeState.THEME_SYSTEM.value -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}

fun getThemeName(context: Context, themeState: String): String = when (themeState) {
    ThemeState.THEME_DARK.value -> context.getString(R.string.theme_dark)
    ThemeState.THEME_LIGHT.value -> context.getString(R.string.theme_light)
    ThemeState.THEME_SYSTEM.value -> context.getString(R.string.theme_system)
    else -> context.getString(R.string.theme_system)
}