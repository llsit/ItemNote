package com.example.itemnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.core.data.utils.SharedViewModel
import com.example.design.theme.ItemNoteTheme
import com.example.feature.note.navigation.mainScreen
import com.example.feature.note.screen.addItem.AddEditItemMode
import com.example.feature.note.screen.addItem.AddEditItemScreen
import com.example.feature.note.screen.authentication.LoginScreen
import com.example.feature.note.screen.authentication.RegisterScreen
import com.example.feature.note.screen.shop.ShopListScreen
import com.example.feature.note.utils.NavigationItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val sharedViewModel: SharedViewModel = hiltViewModel()
    ItemNoteTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = NavigationItem.Login.route
        ) {
            composable(NavigationItem.Login.route) {
                LoginScreen(navController = navController)
            }
            composable(NavigationItem.Register.route) {
                RegisterScreen(navController = navController)
            }
            composable(NavigationItem.Main.route) {
                mainScreen(
                    navController = navController,
                    sharedViewModel = sharedViewModel,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
            mainScreen(
                navController = navController,
                sharedViewModel = sharedViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
            composable(
                "shopList/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType }),
                // defines how the screen enters when navigating forward
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                },
                // defines how the screen exits when navigating forward
                exitTransition = {
                    fadeOut(animationSpec = tween(durationMillis = 500))
                },
                // defines how the screen enters when navigating backward
                popEnterTransition = {
                    fadeIn(animationSpec = tween(durationMillis = 500))
                },
                // defines how the screen exits when navigating backward
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(300)
                    )
                }
            ) {
                ShopListScreen(
                    navController = navController,
                    sharedViewModel = sharedViewModel
                )
            }
            composable(NavigationItem.AddItem.route,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(300)
                    )
                }
            ) {
                AddEditItemScreen(
                    mode = AddEditItemMode.Add,
                    navController = navController,
                    sharedViewModel = sharedViewModel
                )
            }
            composable(NavigationItem.EditItem.route,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(300)
                    )
                }
            ) {
                AddEditItemScreen(
                    mode = AddEditItemMode.Edit,
                    navController = navController,
                    sharedViewModel = sharedViewModel
                )
            }
        }
    }
}
