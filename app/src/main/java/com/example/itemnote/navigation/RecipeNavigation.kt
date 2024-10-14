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
import com.example.favoriterecipe.screen.FavoriteRecipeScreen
import com.example.feature.categorydetail.screen.CategoryListScreen
import com.example.feature.recipe.screen.RecipeHomeScreen
import com.example.feature.recipe.screen.RecipeMainScreen
import com.example.feature.search.screen.SearchScreen

fun NavGraphBuilder.recipeNavigation(
    navController: NavHostController
) {
    composable(NavigationItem.Recipe.route) {
        RecipeMainScreen(
            mainNavController = navController,
            navigateToHome = {
                RecipeHomeScreen(mainNavController = navController, navController = it)
            },
            navigateToSearch = {
                SearchScreen()
            },
            navigateToFavorite = {
                FavoriteRecipeScreen()
            }
        )
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

    composable(
        "categoryList/{categoryName}",
        arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
    ) {
        AnimatedVisibility(
            visible = true,
            enter = slideInHorizontally(animationSpec = tween(300)),
            exit = slideOutHorizontally(animationSpec = tween(300))
        ) {
            CategoryListScreen(
                onBackPressed = { navController.popBackStack() },
                onClick = { navController.navigate("detail/${it}") }
            )
        }
    }
}