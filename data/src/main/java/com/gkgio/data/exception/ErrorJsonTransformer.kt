package com.gkgio.data.exception

import com.gkgio.data.base.BaseServerException
import com.gkgio.data.base.ServerExceptionType
import com.gkgio.data.BaseTransformer
import com.gkgio.data.base.ApiError
import com.gkgio.data.base.ApiResponse
import com.squareup.moshi.Moshi
import javax.inject.Inject

class ErrorJsonTransformer @Inject constructor(
    private val moshi: Moshi
) : BaseTransformer<String, BaseServerException> {

    private companion object {
        private const val UNSUPPORTED_API_VERSION = "UnsupportedApiVersion"
    }

    override fun transform(data: String): BaseServerException {
        val apiError = try {
            moshi.adapter(ApiResponse::class.java).fromJson(data)!!.error
        } catch (e: Exception) {
            ApiError("Проблемы на сервере. Скоро все исправим")
        }
        return when (apiError.code) {
            UNSUPPORTED_API_VERSION -> {
                BaseServerException(
                    "API устарело",
                    ServerExceptionType.UNSUPORTED_VERSION
                )
            }
            else -> BaseServerException(
                apiError.value,
                ServerExceptionType.HTTP_4xxx
            )
        }
    }
}