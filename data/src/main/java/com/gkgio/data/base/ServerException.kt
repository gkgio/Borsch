package com.gkgio.data.base

open class BaseServerException(
    val description: String,
    val type: ServerExceptionType,
    val originalThrowable: Throwable? = null
) : RuntimeException()