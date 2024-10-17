package com.example.favoriterecipe.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.favoriterecipe.component.FavoriteRecipesList

@Composable
fun FavoriteRecipeScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: FavoriteRecipeViewModel = hiltViewModel()
) {
    val favoriteRecipes = viewModel.favoriteRecipes.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        FavoriteRecipesList(
            recipes = favoriteRecipes.value,
            navController = navController,
            onRemove = { viewModel.removeFavoriteRecipe(it) }
        )
    }
}
