package com.example.feature.authentication.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.core.common.navigation.NavigationItem
import com.example.feature.authentication.screen.LoginScreen
import com.example.feature.authentication.screen.RegisterScreen

fun NavGraphBuilder.authenNavigation(navController: NavHostController) {
    composable(NavigationItem.Login.route) {
        LoginScreen(navController = navController)
    }
    composable(NavigationItem.Register.route) {
        RegisterScreen(navController = navController)
    }
}