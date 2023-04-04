package com.example.learning1.utils

import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.properties.Delegates

enum class navigatioType {
    HOME,
    COMPLETED
}

sealed class NavEvent {
//    data class Navigation(val navType: NavType): NavEvent()
    companion object navigation {
    var navType= navigatioType.HOME
    lateinit var navigationBar1: BottomNavigationView
    var DelayMilli by Delegates.notNull<Long>()
}

}