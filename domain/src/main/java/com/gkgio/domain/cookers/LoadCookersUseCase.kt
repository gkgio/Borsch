package com.gkgio.domain.cookers

import com.gkgio.domain.address.LoadAddressesUseCase
import io.reactivex.Single
import javax.inject.Inject

interface LoadCookersUseCase {
    fun loadCookersList(
        distance: String? = null,
        targets: List<String>? = null
    ): Single<List<Cooker>>
}

class LoadCookersUseCaseImpl @Inject constructor(
    private val cookersService: CookersService,
    private val addressesUseCase: LoadAddressesUseCase
) : LoadCookersUseCase {
    override fun loadCookersList(
        distance: String?,
        targets: List<String>?
    ): Single<List<Cooker>> =
        addressesUseCase
            .getLastSavedAddress()
            .flatMap {
                cookersService.loadCookersList(
                    CookersWithoutAuthRequest(
                        0,
                        0,
                        distance,
                        targets
                    )
                )
            }

}