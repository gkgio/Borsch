package com.gkgio.domain.cookers

import com.gkgio.domain.address.LoadAddressesUseCase
import com.gkgio.domain.auth.AuthUseCase
import com.gkgio.domain.cookers.detail.CookerDetail
import io.reactivex.Single
import javax.inject.Inject

interface LoadCookersUseCase {
    fun loadCookersList(
        distance: String? = null,
        targets: List<String>? = null
    ): Single<List<Cooker>>

    fun loadCookerDetail(cookerId: String): Single<CookerDetail>
}

class LoadCookersUseCaseImpl @Inject constructor(
    private val cookersService: CookersService,
    private val addressesUseCase: LoadAddressesUseCase,
    private val authUseCase: AuthUseCase
) : LoadCookersUseCase {
    override fun loadCookersList(
        distance: String?,
        targets: List<String>?
    ): Single<List<Cooker>> =
        addressesUseCase
            .getLastSavedAddress()
            .flatMap {
                val token = authUseCase.getAuthToken()
                if (token != null) {
                    cookersService.loadCookersList(
                        CookersRequest(
                            it.id,
                            distance,
                            targets
                        )
                    )
                } else {
                    cookersService.loadCookersListWithoutAuth(
                        CookersWithoutAuthRequest(
                            it.location.latitude,
                            it.location.longitude,
                            distance,
                            targets
                        )
                    )
                }
            }

    override fun loadCookerDetail(cookerId: String): Single<CookerDetail> {
        val token = authUseCase.getAuthToken()
        return if (token != null) {
            cookersService.loadCookerDetail(cookerId)
        } else {
            cookersService.loadCookerDetailWithoutAuth(cookerId)
        }
    }
}