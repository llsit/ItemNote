package com.example.favoriterecipe.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.core.model.data.RecipeInfo
import com.example.design.ui.EmptyStateView
import com.example.design.ui.FavoriteRecipeItem
import com.example.favoriterecipe.screen.FavoriteRecipeScreen

@Composable
fun FavoriteRecipesList(
    recipes: List<RecipeInfo>,
    navController: NavHostController,
    onRemove: (String) -> Unit
) {
    if (recipes.isEmpty()) {
        EmptyStateView(
            message = "No favorite recipes yet!"
        )
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(recipes) { recipe ->
                FavoriteRecipeItem(
                    title = recipe.title,
                    category = recipe.category,
                    imageUrl = recipe.imageUrl.orEmpty(),
                    recipeId = recipe.id,
                    isFavorite = recipe.isFavorite,
                    onClick = { navController.navigate("detail/${recipe.id}") },
                    onRemove = onRemove
                )
            }
        }
    }
}

@Preview
@Composable
fun FavoriteRecipeScreenPreview() {
    FavoriteRecipeScreen()
}
