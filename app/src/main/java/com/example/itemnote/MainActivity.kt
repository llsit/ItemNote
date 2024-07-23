package com.example.itemnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.itemnote.screen.addItem.AddItemScreen
import com.example.itemnote.screen.authentication.LoginScreen
import com.example.itemnote.screen.authentication.RegisterScreen
import com.example.itemnote.screen.main.MainScreen
import com.example.itemnote.screen.shop.ShopListScreen
import com.example.itemnote.ui.theme.ItemNoteTheme
import com.example.itemnote.utils.NavigationItem
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
                MainScreen(navController = navController, sharedViewModel = sharedViewModel)
            }
            composable(
                "shopList/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) {
                ShopListScreen(navController = navController, sharedViewModel = sharedViewModel)
            }
            composable(NavigationItem.AddItem.route) {
                AddItemScreen(navController = navController)
            }
        }
    }
}
