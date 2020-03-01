package com.gkgio.data.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ApiResponse(
    @Json(name = "errors")
    val error: ApiError
)

@JsonClass(generateAdapter = true)
class ApiError(
    @Json(name = "detail")
    val detail: String? = null,
    @Json(name = "code")
    val code: String? = null
) {
    val value = detail ?: ""
}