package com.example.feature.note.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.core.data.utils.SharedViewModel
import com.example.feature.note.screen.main.MainScreen
import com.example.feature.note.utils.NavigationItem

fun NavController.navigateToMain(navOptions: NavOptions? = null) =
    navigate(NavigationItem.Main.route, navOptions)

fun NavGraphBuilder.mainScreen(
    onBackClick: () -> Unit,
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
) {
    composable(route = NavigationItem.Main.route) {
        MainScreen(
            navController = navController,
            sharedViewModel = sharedViewModel,
            onBackClick = onBackClick
        )
    }
}