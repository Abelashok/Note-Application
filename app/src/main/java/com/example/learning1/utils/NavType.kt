package com.example.learning1.utils

sealed class NavType {
    object Home: NavType()
    object Completed: NavType()
    object Add: NavType()
}