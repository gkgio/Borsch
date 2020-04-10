package com.gkgio.domain.basket

import com.gkgio.domain.cookers.detail.CookerAddress
import java.math.BigDecimal

class BasketCountAndSum(
    val count: Int,
    val sum: BigDecimal,
    val cookerId: String,
    val cookerAddress: CookerAddress?
)