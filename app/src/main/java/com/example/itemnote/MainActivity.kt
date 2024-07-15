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
import com.example.itemnote.ui.theme.ItemNoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ItemNoteTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "mainScreen") {
                    composable("mainScreen") {
                        MainScreen(navController = navController)
                    }
                    composable("shopList/{id}", arguments = listOf(navArgument("id") { type = NavType.StringType })) {
                        ShopListScreen(navController = navController, it.arguments?.getString("id"))
                    }
                    composable("addItem") {
                        AddItemScreen(navController = navController)
                    }
                }

            }
        }
    }
}