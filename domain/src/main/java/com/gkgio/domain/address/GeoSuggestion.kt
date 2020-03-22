package com.gkgio.domain.address

data class GeoSuggestion(
    val data: GeoSuggestionData,
    val value: String //то что показывается клиенту
)