package com.example.itemnote.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.core.common.navigation.NavigationItem
import com.example.detail.screen.DetailScreen
import com.example.feature.note.screen.shop.ShopListScreen
import com.example.feature.recipe.screen.RecipeMainScreen

fun NavGraphBuilder.recipeNavigation(
    navController: NavHostController
) {
    composable(NavigationItem.Recipe.route) {
        RecipeMainScreen(navController = navController)
    }

    composable(
        "detail/{recipeId}",
        arguments = listOf(navArgument("recipeId") { type = NavType.StringType })
    ) {
        AnimatedVisibility(
            visible = true,
            enter = slideInHorizontally(animationSpec = tween(300)),
            exit = slideOutHorizontally(animationSpec = tween(300))
        ) {
            DetailScreen(
                onBackPressed = { navController.popBackStack() }
            )
        }
    }

}