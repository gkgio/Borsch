package com.gkgio.borsch.orders

enum class OrderStatus(val type: String, val value: String) {
    COMPLETED("completed", "Завершенный"),
    CAN_PICKUP("canPickup", "Можно забирать"),
    DELIVERING("delivering", "Заказ в пути"),
    REJECTED("rejected", "Отказ"),
    CANCELED("canceled", "Отмененный"),
    ACCEPTED("accepted", "Подтвержденный"),
    COOKING("cooking", "Готовка"),
    CREATED("created", "Создан")
}