package com.gkgio.domain.onboarding

interface OnboardingRepository {
    fun saveVersion(version: Int)
    fun getVersion(): Int
    val isFirstStart: Boolean
}