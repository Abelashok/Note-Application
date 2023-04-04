package com.example.learning1.utils

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
    object OrderByDate: OrderType()
}