package com.gkgio.domain.errorreporter

interface ErrorReporter {
    fun log(throwable: Throwable)
}