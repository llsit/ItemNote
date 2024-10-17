package com.example.core.model.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavigationItem(
    val icon: ImageVector,
    val label: String,
    val route: String,
    val selectedTabIndex: Int
) {
    Home(
        icon = Icons.Filled.Home,
        label = "Home",
        route = "home",
        selectedTabIndex = 0
    ),
    Search(
        icon = Icons.Filled.Search,
        label = "Search",
        route = "search",
        selectedTabIndex = 1
    ),
    Favorite(
        icon = Icons.Filled.Favorite,
        label = "Favorite",
        route = "favorite",
        selectedTabIndex = 2
    )
}