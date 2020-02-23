package com.gkgio.borsch.di

import com.gkgio.data.theme.ThemeRepositoryImpl
import com.gkgio.domain.theme.ThemeRepository
import dagger.Binds
import dagger.Module

@Module
abstract class ThemeModule {

    @Binds
    abstract fun themeRepository(arg0 : ThemeRepositoryImpl) : ThemeRepository
}