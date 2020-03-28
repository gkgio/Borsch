package com.gkgio.borsch.location.saved

import com.gkgio.domain.address.Address

data class AddressUi(
    val address: Address,
    var isSelected: Boolean
)