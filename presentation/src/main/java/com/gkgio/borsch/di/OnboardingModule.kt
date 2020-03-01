package com.gkgio.borsch.di

import com.gkgio.data.onboarding.OnboardingRepositoryImpl
import com.gkgio.domain.onboarding.OnboardingRepository
import com.gkgio.domain.onboarding.OnboardingVersionControllerUseCase
import com.gkgio.domain.onboarding.OnboardingVersionControllerUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
abstract class OnboardingModule {

    @Binds
    abstract fun onboardingRepository(arg: OnboardingRepositoryImpl): OnboardingRepository

    @Binds
    abstract fun onboardingUseCase(arg: OnboardingVersionControllerUseCaseImpl): OnboardingVersionControllerUseCase
}