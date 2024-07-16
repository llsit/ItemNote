package com.example.itemnote.utils

enum class Screen {
    MAIN,
    ADDITEM,
    SHOPLIST,
    REGISTER,
    LOGIN
}

sealed class NavigationItem(val route: String) {
    object Main : NavigationItem(Screen.MAIN.name)
    object Login : NavigationItem(Screen.LOGIN.name)
    object Register : NavigationItem(Screen.REGISTER.name)
    object AddItem : NavigationItem(Screen.ADDITEM.name)
    object ShopList : NavigationItem(Screen.SHOPLIST.name)
}