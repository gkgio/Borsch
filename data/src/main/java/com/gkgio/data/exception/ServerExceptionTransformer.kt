package com.gkgio.data.exception

import com.gkgio.data.base.BaseServerException
import com.gkgio.data.base.ServerExceptionType
import com.gkgio.data.BaseTransformer
import com.gkgio.data.base.ApiError
import com.gkgio.data.base.ApiResponse
import com.gkgio.data.ext.getExceptionType
import com.gkgio.domain.errorreporter.ErrorReporter
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class ServerExceptionTransformer @Inject constructor(
    private val moshi: Moshi,
    private val errorReporter: ErrorReporter
) : BaseTransformer<Throwable, BaseServerException> {

    override fun transform(data: Throwable) = when (data) {
        is HttpException -> {
            createBaseServerException(
                getApiErrorFromHttpException(data),
                data
            )
        }
        is UnknownHostException -> {
            BaseServerException(
                "Нет интернет-соединения",
                ServerExceptionType.NO_INTERNET,
                data
            )
        }
        is BaseServerException -> data
        else -> BaseServerException(
            "Что-то пошло не так. Попробуй еще раз",
            ServerExceptionType.UNKNOWN,
            data
        )
    }

    private fun createBaseServerException(
        apiError: ApiError,
        httpException: HttpException
    ): BaseServerException {
        if (httpException.getExceptionType() == ServerExceptionType.HTTP_4xxx) {
            errorReporter.log(httpException)
        }
        return BaseServerException(
            apiError.value,
            httpException.getExceptionType(),
            httpException
        )
    }


    private fun getApiErrorFromHttpException(httpException: HttpException): ApiError = try {
        val json = httpException.response()!!.errorBody()!!.string()
        moshi.adapter(ApiResponse::class.java).fromJson(json)!!.error
    } catch (e: Exception) {
        ApiError("Проблемы на сервере. Скоро все исправим")
    }
}