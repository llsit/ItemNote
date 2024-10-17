package com.example.feature.recipe.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.model.navigation.BottomNavigationItem

@Composable
fun BottomNavigationBar(navController: NavController = rememberNavController()) {
    val currentDestination by navController.currentBackStackEntryAsState()

    val selectedItem = BottomNavigationItem.entries.find {
        it.route == currentDestination?.destination?.route
    }?.selectedTabIndex ?: 0

    val items = BottomNavigationItem.entries.toTypedArray()

    Box {
        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = { Icon(item.icon, contentDescription = item.label) },
                    label = { Text(item.label) },
                    selected = selectedItem == index,
                    onClick = {
                        navController.navigate(item.route){
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
