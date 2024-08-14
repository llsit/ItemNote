package com.example.recipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipe.screen.detail.RecipeDetailScreen
import com.example.recipe.screen.main.RecipeMainScreen
import com.example.recipe.ui.theme.ItemNoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ItemNoteTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "main") {
                    composable(route = "main") {
                        RecipeMainScreen()
                    }

                    composable(route = "detail") {
                        RecipeDetailScreen()
                    }
                }
            }
        }
    }
}
