package com.gkgio.data.ext

import com.gkgio.data.base.ServerExceptionType
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

fun HttpException.getExceptionType() = when (this.code() / 100) {
    4 -> ServerExceptionType.HTTP_4xxx
    5 -> ServerExceptionType.HTTP_5xxx
    else -> ServerExceptionType.UNKNOWN
}

fun ResponseBody?.getErrorJsonIfPossible(): String? {
    this?.let {
        source().request(Long.MAX_VALUE)
        val buffer = source().buffer

        val contentType = contentType()
        val charset: Charset =
            contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8

        val json = buffer.clone().readString(charset)
        return if (json.contains("\"error\":{")) {
            json
        } else {
            null
        }
    }
    return null
}