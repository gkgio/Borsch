package com.gkgio.borsch.di

import com.gkgio.data.auth.AuthRepositoryImpl
import com.gkgio.domain.auth.AuthRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AuthModule {
    @Singleton
    @Binds
    abstract fun authRepository(arg: AuthRepositoryImpl): AuthRepository
}