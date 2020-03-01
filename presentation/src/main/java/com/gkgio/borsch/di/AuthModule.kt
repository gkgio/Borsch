package com.gkgio.borsch.di

import com.gkgio.data.auth.AuthRepositoryImpl
import com.gkgio.data.auth.AuthServiceImpl
import com.gkgio.domain.auth.AuthRepository
import com.gkgio.domain.auth.AuthService
import com.gkgio.domain.auth.AuthUseCase
import com.gkgio.domain.auth.AuthUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [AuthModule.BindsModule::class])
class AuthModule {

    @Provides
    fun authServiceApi(retrofit: Retrofit): AuthServiceImpl.AuthServiceApi = retrofit.create()

    @Module
    abstract inner class BindsModule {
        @Binds
        abstract fun authRepository(arg: AuthRepositoryImpl): AuthRepository

        @Binds
        abstract fun authService(arg: AuthServiceImpl): AuthService

        @Binds
        abstract fun authUseCase(arg: AuthUseCaseImpl): AuthUseCase
    }
}