package com.gkgio.borsch.di

import com.gkgio.data.cookers.CookersServiceImpl
import com.gkgio.domain.cookers.CookersService
import com.gkgio.domain.cookers.LoadCookersUseCase
import com.gkgio.domain.cookers.LoadCookersUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.Binds
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [CookersModule.BindsModule::class])
class CookersModule {
    @Provides
    fun cookersServiceApi(retrofit: Retrofit): CookersServiceImpl.CookersServiceApi =
        retrofit.create()

    @Module
    abstract inner class BindsModule {

        @Binds
        abstract fun cookersService(arg: CookersServiceImpl): CookersService

        @Binds
        abstract fun cookersUseCase(arg: LoadCookersUseCaseImpl): LoadCookersUseCase
    }
}