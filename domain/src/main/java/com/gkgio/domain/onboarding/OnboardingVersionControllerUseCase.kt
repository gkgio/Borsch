package com.gkgio.domain.onboarding

import javax.inject.Inject

interface OnboardingVersionControllerUseCase {
    fun isSavedVersionActual(): Boolean
    fun saveActualVersion()
}

class OnboardingVersionControllerUseCaseImpl @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) : OnboardingVersionControllerUseCase {

    companion object {
        const val ACTUAL_VERSION = 1
    }

    override fun isSavedVersionActual(): Boolean {
        val savedVersion = onboardingRepository.getVersion()
        return savedVersion == ACTUAL_VERSION
    }

    override fun saveActualVersion() = onboardingRepository.saveVersion(ACTUAL_VERSION)
}