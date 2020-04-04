package com.gkgio.borsch.cookers

enum class CookerFilerEnum(private val nameFilter: String, val value: String) {
    DELIVERY("delivery", "Доставка"),
    PICKUP("pickup", "Самовывоз");

    fun getValueByName(nameFilter: String): String? {
        for (item in values()) {
            if (item.nameFilter == nameFilter) {
                return item.value
            }
        }
        return null
    }
}