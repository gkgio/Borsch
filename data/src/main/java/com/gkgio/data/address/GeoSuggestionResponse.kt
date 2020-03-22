package com.gkgio.data.address


data class GeoSuggestionResponse(
    val data: GeoSuggestionDataResponse,
    val value: String //то что показывается клиенту
)