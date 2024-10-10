package com.example.feature.recipe.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomNavigationBar(navController: NavController = rememberNavController()) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = BottomNavigationItem.entries.toTypedArray()

    Box {
        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = { Icon(item.icon, contentDescription = item.label) },
                    label = { Text(item.label) },
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        navController.navigate(item.route)
                    }
                )
            }
        }
    }
}

enum class BottomNavigationItem(
    val icon: ImageVector,
    val label: String,
    val route: String
) {
    Home(
        icon = Icons.Filled.Home,
        label = "Home",
        route = "home"
    ),
    Search(
        icon = Icons.Filled.Search,
        label = "Search",
        route = "search"
    ),
    Favorite(
        icon = Icons.Filled.Favorite,
        label = "Favorite",
        route = "favorite"
    )
}