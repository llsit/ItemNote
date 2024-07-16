package com.example.itemnote.utils

enum class Screen {
    HOME,
    ADDITEM,
    SHOPLIST
}

sealed class NavigationItem(val route: String) {
    object Home : NavigationItem(Screen.HOME.name)
    object ADDITEM : NavigationItem(Screen.ADDITEM.name)
    object SHOPLIST : NavigationItem(Screen.SHOPLIST.name)
}