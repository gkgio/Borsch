package com.gkgio.domain.theme

interface ThemeRepository {
    fun loadTheme(): String
    fun saveTheme(themeState: String)
}