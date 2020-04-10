package com.gkgio.borsch.basket

import java.math.BigDecimal


class BasketDataUi(
    val name: String,
    val id: String,
    var count: Int,
    var price: String,
    var priceOneItem: BigDecimal
)