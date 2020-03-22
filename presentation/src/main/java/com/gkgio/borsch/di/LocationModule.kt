package com.gkgio.borsch.di

import com.gkgio.data.address.adding.AddressesServiceImpl
import com.gkgio.data.address.repository.AddressesRepositoryImpl
import com.gkgio.data.location.LocationRepositoryImpl
import com.gkgio.domain.address.AddressesRepository
import com.gkgio.domain.address.AddressesService
import com.gkgio.domain.address.LoadAddressesUseCase
import com.gkgio.domain.address.LoadAddressesUseCaseImpl
import com.gkgio.domain.location.LoadLocationUseCase
import com.gkgio.domain.location.LoadLocationUseCaseImpl
import com.gkgio.domain.location.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create


@Module(includes = [LocationModule.BindsModule::class])
class LocationModule {

    @Provides
    fun addressServiceApi(retrofit: Retrofit): AddressesServiceImpl.AddressServiceApi =
        retrofit.create()

    @Module
    abstract inner class BindsModule {
        @Binds
        abstract fun loadLocationUseCase(arg: LoadLocationUseCaseImpl): LoadLocationUseCase

        @Binds
        abstract fun locationRepository(arg: LocationRepositoryImpl): LocationRepository

        @Binds
        abstract fun loadAddressesUseCase(arg: LoadAddressesUseCaseImpl): LoadAddressesUseCase

        @Binds
        abstract fun addressService(arg: AddressesServiceImpl): AddressesService

        @Binds
        abstract fun addressRepository(arg: AddressesRepositoryImpl): AddressesRepository
    }
}