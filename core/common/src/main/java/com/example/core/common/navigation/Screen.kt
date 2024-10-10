package com.example.core.common.navigation

enum class Screen {
    MAIN,
    ADDITEM,
    SHOPLIST,
    REGISTER,
    LOGIN,
    EDITITEM,
    AUTHENTICATION,
    RECIPE,
    FAVORITE,
    SEARCH,
    HOME
}

sealed class NavigationItem(val route: String) {
    object Main : NavigationItem(Screen.MAIN.name)
    object Login : NavigationItem(Screen.LOGIN.name)
    object Register : NavigationItem(Screen.REGISTER.name)
    object AddItem : NavigationItem(Screen.ADDITEM.name)
    object EditItem : NavigationItem(Screen.EDITITEM.name)
    object ShopList : NavigationItem(Screen.SHOPLIST.name) {
        fun createRoute(id: String) = "$route/$id"
    }
    object Authentication : NavigationItem(Screen.AUTHENTICATION.name)
    object Recipe : NavigationItem(Screen.RECIPE.name)
    object Favorite : NavigationItem(Screen.FAVORITE.name)
    object Search : NavigationItem(Screen.SEARCH.name)
    object Home : NavigationItem(Screen.HOME.name)

}