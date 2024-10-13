package com.example.feature.recipe.screen

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core.common.navigation.NavigationItem
import com.example.design.ui.ToolbarScreen
import com.example.feature.recipe.component.BottomNavigationBar

@Composable
fun RecipeMainScreen(
    mainNavController: NavHostController,
    navigateToHome: @Composable () -> Unit,
    navigateToSearch: () -> Unit = {},
    navigateToFavorite: @Composable () -> Unit
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            ToolbarScreen(
                title = "Recipe",
                isBack = true,
                onBackClick = {
                    mainNavController.popBackStack()
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = NavigationItem.Home.route,
            modifier = Modifier
                .padding(innerPadding)
        ) {
            composable(NavigationItem.Home.route) { navigateToHome() }
            composable(NavigationItem.Search.route) { navigateToSearch() }
            composable(NavigationItem.Favorite.route) { navigateToFavorite() }
        }
    }
}
