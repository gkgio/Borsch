package com.gkgio.domain.basket

import com.gkgio.domain.cookers.Cooker

data class OrderDetailData(
    val cooker: Cooker,
    val order: OrderDetail
)