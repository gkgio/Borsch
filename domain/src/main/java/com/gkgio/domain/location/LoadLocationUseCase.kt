package com.gkgio.domain.location

import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

/**
 * Сценарий использования "Получение геолокации", где инкапсулирована
 * логика для получения геолокации на время сессии пользователя в приложении
 */
interface LoadLocationUseCase {
    fun getLocationOrThrow(): Single<Coordinates>
    fun getLocationIfPossible(): Maybe<Coordinates>
}

class LoadLocationUseCaseImpl @Inject constructor(
    private val locationRepository: LocationRepository
) : LoadLocationUseCase {

    override fun getLocationOrThrow(): Single<Coordinates> {
        return locationRepository.getLastKnownLocationOrThrow()
    }

    override fun getLocationIfPossible(): Maybe<Coordinates> {
        return locationRepository.getLastKnownLocationIfPossible()
    }
}