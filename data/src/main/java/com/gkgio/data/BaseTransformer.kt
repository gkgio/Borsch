package com.gkgio.data

interface BaseTransformer<T, R> {
    fun transform(data: T): R
}