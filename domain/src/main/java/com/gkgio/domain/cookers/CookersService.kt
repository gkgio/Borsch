package com.gkgio.domain.cookers

import io.reactivex.Single

interface CookersService {
    fun loadCookersList(cookersRequest: CookersWithoutAuthRequest): Single<List<Cooker>>
}