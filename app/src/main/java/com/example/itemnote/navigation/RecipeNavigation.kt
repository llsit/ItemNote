package com.example.itemnote.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.core.common.navigation.NavigationItem
import com.example.feature.recipe.screen.RecipeMainScreen

fun NavGraphBuilder.recipeNavigation(
    navController: NavHostController
) {
    composable(NavigationItem.Recipe.route) {
        RecipeMainScreen(navController = navController)
    }

}