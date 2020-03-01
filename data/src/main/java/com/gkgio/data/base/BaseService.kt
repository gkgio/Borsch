package com.gkgio.data.base

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function
import com.gkgio.data.exception.ServerExceptionTransformer

abstract class BaseService(private val serverExceptionTransformer: ServerExceptionTransformer) {

    fun <T> executeRequest(single: Single<T>): Single<T> = single.onErrorResumeNext {
        Single.error(serverExceptionTransformer.transform(it))
    }

    fun <T> executeRequest(observable: Observable<T>): Observable<T> = observable.onErrorResumeNext(
        Function { Observable.error(serverExceptionTransformer.transform(it)) }
    )

    fun executeRequest(completable: Completable): Completable = completable.onErrorResumeNext {
        Completable.error(serverExceptionTransformer.transform(it))
    }
}