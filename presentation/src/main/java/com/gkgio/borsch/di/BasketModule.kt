package com.gkgio.borsch.di

import com.gkgio.data.basket.BasketRepositoryImpl
import com.gkgio.data.basket.BasketServiceImpl
import com.gkgio.domain.basket.BasketRepository
import com.gkgio.domain.basket.BasketService
import com.gkgio.domain.basket.BasketUseCase
import com.gkgio.domain.basket.BasketUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create


@Module(includes = [BasketModule.BindsModule::class])
class BasketModule {

    @Provides
    fun authServiceApi(retrofit: Retrofit): BasketServiceImpl.BasketServiceApi = retrofit.create()

    @Module
    abstract inner class BindsModule {

        @Binds
        abstract fun basketService(arg: BasketServiceImpl): BasketService

        @Binds
        abstract fun basketRepository(arg: BasketRepositoryImpl): BasketRepository

        @Binds
        abstract fun basketUseCase(arg: BasketUseCaseImpl): BasketUseCase
    }
}