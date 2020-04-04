package com.gkgio.domain.cookers

import com.gkgio.domain.cookers.detail.CookerDetail
import io.reactivex.Single

interface CookersService {
    fun loadCookersList(cookersRequest: CookersRequest): Single<List<Cooker>>
    fun loadCookersListWithoutAuth(cookersRequest: CookersWithoutAuthRequest): Single<List<Cooker>>
    fun loadCookerDetail(cookerId: String): Single<CookerDetail>
    fun loadCookerDetailWithoutAuth(cookerId: String): Single<CookerDetail>
}