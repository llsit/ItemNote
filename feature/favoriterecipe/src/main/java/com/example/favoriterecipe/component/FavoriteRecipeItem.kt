package com.example.favoriterecipe.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.core.design.R
import com.example.core.model.data.RecipeInfo
import com.example.design.ui.EmptyStateView
import com.example.design.ui.FavoriteComponent
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
                    recipe = recipe,
                    onClick = { navController.navigate("detail/${recipe.id}") },
                    onRemove = onRemove
                )
            }
        }
    }
}

@Composable
fun FavoriteRecipeItem(
    recipe: RecipeInfo,
    onClick: () -> Unit,
    onRemove: (String) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.title,
                placeholder = painterResource(id = R.drawable.placeholder_product),
                error = painterResource(id = R.drawable.placeholder_product),
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = recipe.title, style = MaterialTheme.typography.labelMedium)
                Text(text = recipe.category, style = MaterialTheme.typography.titleSmall)
            }

            FavoriteComponent(
                isFavorite = recipe.isFavorite,
                onFavoriteClick = {
                    onRemove(recipe.id)
                }
            )
        }
    }
}

@Preview
@Composable
fun FavoriteRecipeScreenPreview() {
    FavoriteRecipeScreen()
}
