package com.example.itemnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.itemnote.screen.AddItemScreen
import com.example.itemnote.screen.ShopListScreen
import com.example.itemnote.screen.authentication.LoginScreen
import com.example.itemnote.screen.authentication.RegisterScreen
import com.example.itemnote.ui.theme.ItemNoteTheme
import com.example.itemnote.utils.NavigationItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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
                        MainScreen(navController = navController)
                    }
                    composable(
                        "shopList/{id}",
                        arguments = listOf(navArgument("id") { type = NavType.StringType })
                    ) {
                        ShopListScreen(navController = navController, it.arguments?.getString("id"))
                    }
                    composable(NavigationItem.AddItem.route) {
                        AddItemScreen(navController = navController)
                    }
                }

            }
        }
    }
}