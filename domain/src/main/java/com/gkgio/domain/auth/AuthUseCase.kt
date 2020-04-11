package com.gkgio.domain.auth

import com.gkgio.domain.address.AddressAddingRequest
import com.gkgio.domain.address.AddressesRepository
import com.gkgio.domain.address.AddressesService
import com.gkgio.domain.address.LoadAddressesUseCase
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface AuthUseCase {
    fun getSmsCodeByPhone(inputPhone: String): Single<GetSmsCode>
    fun validateSmsCode(token: String, code: String): Single<ValidateSmsCode>
    fun getAuthToken(): String?
    fun saveAuthToken(token: String)
    fun saveUserProfile(user: User)
    fun loadUserProfile(): User?
}

class AuthUseCaseImpl @Inject constructor(
    private val authService: AuthService,
    private val authRepository: AuthRepository,
    private val addressesRepository: AddressesRepository,
    private val addressesService: AddressesService
) : AuthUseCase {

    override fun getSmsCodeByPhone(inputPhone: String): Single<GetSmsCode> =
        authService.getSmsCodeByPhone(inputPhone)

    override fun validateSmsCode(token: String, code: String): Single<ValidateSmsCode> =
        authService.validateSmsCode(token, code)
            .flatMap { validateSmsCode ->
                saveAuthToken(validateSmsCode.token)
                saveUserProfile(validateSmsCode.user)
                saveLastAddressAfterAuth()
                    .andThen(
                        Single.fromCallable { validateSmsCode }
                    )
            }

    override fun getAuthToken(): String? =
        authRepository.getAuthToken()

    override fun saveAuthToken(token: String) =
        authRepository.saveAuthToken(token)

    override fun saveUserProfile(user: User) =
        authRepository.saveUserProfile(user)

    override fun loadUserProfile(): User? =
        authRepository.loadUserProfile()

    private fun saveLastAddressAfterAuth(): Completable =
        addressesRepository.getSavedAddresses()
            .flatMapCompletable { listAddresses ->
                val lastAddedAddress = listAddresses[0]
                addressesService.addSelectedAddress(
                    AddressAddingRequest(
                        lastAddedAddress.city,
                        lastAddedAddress.country,
                        lastAddedAddress.flat,
                        lastAddedAddress.house,
                        lastAddedAddress.location,
                        lastAddedAddress.street,
                        lastAddedAddress.block
                    )
                ).flatMapCompletable {
                    addressesRepository.saveLastKnownAddress(it)
                }
            }
}