package com.gkgio.domain.basket

import java.math.BigDecimal

data class BasketData(
    val name: String,
    val id: String,
    var count: Int,
    val imageUrl: String?,
    var price: BigDecimal,
    var priceOneItem: BigDecimal,
    var type: Int
)