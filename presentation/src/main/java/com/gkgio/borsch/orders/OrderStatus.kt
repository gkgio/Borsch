package com.gkgio.borsch.orders

enum class OrderStatus(val type: String, val value: String) {
    COMPLETED("completed", "Завершенный"),
    REJECTED("rejected", "Отказ"),
    CANCELED("canceled", "Отмененный"),
    ACCEPTED("accepted", "Подтвержденный"),
    COOKING("cooking", "Готовка"),
    CREATED("created", "Создан")
}