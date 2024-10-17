package com.example.itemnote.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.core.common.navigation.NavigationItem
import com.example.feature.authentication.navigation.authenNavigation
import com.example.feature.note.navigation.noteNavigation

@Composable
fun MainNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Login.route
    ) {
        authenNavigation(
            navController = navController
        )
        noteNavigation(
            navController = navController,
            onNavigateToRecipe = {
                navController.navigate(NavigationItem.Recipe.route)
            }
        )
        recipeNavigation(
            navController = navController
        )
    }
}