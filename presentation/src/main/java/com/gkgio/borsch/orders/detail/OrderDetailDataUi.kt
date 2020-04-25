package com.gkgio.borsch.orders.detail

import com.gkgio.borsch.cookers.detail.CookerUi

data class OrderDetailDataUi(
    val cooker: CookerUi,
    val order: OrderDetailUi
)