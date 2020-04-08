package com.gkgio.domain.basket

import java.math.BigDecimal

data class BasketData(
    val name: String,
    val id: String,
    var count: Int,
    var price: BigDecimal
)