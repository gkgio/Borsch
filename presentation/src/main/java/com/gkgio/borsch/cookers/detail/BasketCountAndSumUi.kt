package com.gkgio.borsch.cookers.detail

data class BasketCountAndSumUi(
    val count: Int,
    val sum: String,
    val cookerId: String,
    val cookerAddressUi: CookerAddressUi?
)