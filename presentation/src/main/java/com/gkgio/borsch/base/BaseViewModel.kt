package com.gkgio.borsch.base

import androidx.lifecycle.ViewModel
import com.gkgio.borsch.utils.SingleLiveEvent
import com.gkgio.data.base.BaseServerException
import com.gkgio.data.base.ServerExceptionType
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

abstract class BaseViewModel(
    private val screensNavigator: BaseScreensNavigator
    ) : ViewModel() {

    protected val disposables = CompositeDisposable()

    val errorEvent = SingleLiveEvent<String>()
    val noInternetErrorEvent = SingleLiveEvent<String>()
    val unsupportedVersionErrorEvent = SingleLiveEvent<String>()
    val googleGmsApiNotConnectErrorEvent = SingleLiveEvent<Unit>()

    protected fun Disposable.addDisposable() {
        disposables.add(this)
    }

    protected fun processThrowable(throwable: Throwable, title: String? = null) {
        when (throwable) {
            is BaseServerException -> {
                Timber.e(throwable.originalThrowable ?: throwable, title)
                when (throwable.type) {
                    ServerExceptionType.UNSUPORTED_VERSION -> unsupportedVersionErrorEvent.value =
                        throwable.description
                    ServerExceptionType.NO_INTERNET -> noInternetErrorEvent.value =
                        throwable.description
                    else -> errorEvent.value = throwable.description
                }
            }
            is ApiException -> { //Gms google api exception
                if (throwable.statusCode == CommonStatusCodes.API_NOT_CONNECTED) {
                    googleGmsApiNotConnectErrorEvent.call()
                } else {
                    errorEvent.value = throwable.message
                }
                Timber.e(throwable)
            }
            else -> {
                Timber.e(throwable, title)
                errorEvent.call()
            }
        }
    }

    fun onMarketClick(packageName: String) = screensNavigator.navigateToMarket(packageName)

    open fun onBackClick() {
        screensNavigator.navigateBack()
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}