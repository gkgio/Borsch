package com.gkgio.domain.cookers

import io.reactivex.Single
import javax.inject.Inject

interface LoadCookersUseCase {
    fun loadCookersList(cookersRequest: CookersRequest): Single<List<Cooker>>
}

class LoadCookersUseCaseImpl @Inject constructor(
    private val cookersService: CookersService
) : LoadCookersUseCase {
    override fun loadCookersList(cookersRequest: CookersRequest): Single<List<Cooker>> =
        cookersService.loadCookersList(cookersRequest)

}