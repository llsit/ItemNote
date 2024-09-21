package com.example.feature.note.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.core.common.navigation.NavigationItem
import com.example.feature.note.screen.addItem.AddEditItemMode
import com.example.feature.note.screen.addItem.AddEditItemScreen
import com.example.feature.note.screen.main.MainScreen
import com.example.feature.note.screen.shop.ShopListScreen

fun NavController.navigateToMain(navOptions: NavOptions? = null) =
    navigate(NavigationItem.Main.route, navOptions)

fun NavGraphBuilder.noteNavigation(
    onNavigateToRecipe: () -> Unit,
    navController: NavHostController,
) {
    composable(route = NavigationItem.Main.route) {
        MainScreen(
            navController = navController,
            onNavigateToRecipe = onNavigateToRecipe
        )
    }
    composable(
        "shopList/{id}",
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) {
        AnimatedVisibility(
            visible = true,
            enter = slideInHorizontally(animationSpec = tween(300)),
            exit = slideOutHorizontally(animationSpec = tween(300))
        ) {
            ShopListScreen(
                navController = navController,
            )
        }
    }
    composable(NavigationItem.AddItem.route) {
        AnimatedVisibility(
            visible = true,
            enter = slideInHorizontally(animationSpec = tween(300)),
            exit = slideOutHorizontally(animationSpec = tween(300))
        ) {
            AddEditItemScreen(
                mode = AddEditItemMode.Add,
                navController = navController,
            )
        }
    }
    composable(NavigationItem.EditItem.route) {
        AnimatedVisibility(
            visible = true,
            enter = slideInHorizontally(animationSpec = tween(300)),
            exit = slideOutHorizontally(animationSpec = tween(300))
        ) {
            AddEditItemScreen(
                mode = AddEditItemMode.Edit,
                navController = navController,
            )
        }
    }
}