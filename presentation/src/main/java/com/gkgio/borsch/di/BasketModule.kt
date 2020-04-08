package com.gkgio.borsch.di

import com.gkgio.data.basket.BasketRepositoryImpl
import com.gkgio.domain.basket.BasketRepository
import com.gkgio.domain.basket.BasketUseCase
import com.gkgio.domain.basket.BasketUseCaseImpl
import dagger.Binds
import dagger.Module


@Module(includes = [BasketModule.BindsModule::class])
class BasketModule {

    @Module
    abstract inner class BindsModule {

        @Binds
        abstract fun basketRepository(arg: BasketRepositoryImpl): BasketRepository

        @Binds
        abstract fun basketUseCase(arg: BasketUseCaseImpl): BasketUseCase
    }
}