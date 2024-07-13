package com.example.itemnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                    composable("shopList") {
                        ShopListScreen(navController = navController)
                    }
                    composable("addItem") {
                        AddItemScreen(navController = navController)
                    }
                }

            }
        }
    }
}