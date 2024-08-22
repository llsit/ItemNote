package com.example.detail.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.design.R
import com.example.core.model.data.IngredientInfo

@Composable
fun DetailScreen(
    viewModel: RecipeDetailViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onBookmarkToggled: () -> Unit = {}
) {
    val recipeInfo by viewModel.recipeInfo.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Choco Macarons") },
                modifier = Modifier.background(color = Color.Transparent),
                navigationIcon = {
                    IconButton(
                        onClick = onBackPressed,
                        modifier = Modifier.background(
                            color = Color.LightGray.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onBookmarkToggled) {
                        Icon(
                            if (recipeInfo.isBookmarked.or(false)) Icons.Filled.Bookmark else Icons.Outlined.Bookmark,
                            contentDescription = "Bookmark",
                            tint = if (recipeInfo!!.isBookmarked) Color.Red else Color.Black
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.placeholder_product),
                    contentDescription = recipeInfo.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    recipeInfo.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
//                Text(
//                    "${recipeInfo.prepTime} mins • ${"Medium"} • ${recipeInfo.calories.toInt()} cal",
//                    style = MaterialTheme.typography.titleSmall,
//                    modifier = Modifier.fillMaxWidth(),
//                    color = Color.Gray
//                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Description",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    recipeInfo.description,
                    modifier = Modifier
                        .fillMaxWidth(),
                    maxLines = 3,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Ingredients",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                recipeInfo.ingredients.forEach { ingredient ->
                    IngredientItem(ingredient)
                }

                Button(
                    onClick = {  },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Watch Videos")
                }
            }
        }
    }
}

@Composable
fun IngredientItem(ingredient: IngredientInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.placeholder_product),
            contentDescription = ingredient.name,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(ingredient.name)
        Spacer(modifier = Modifier.weight(1f))
        Text(ingredient.amount)
    }
}

@Composable
@Preview(showBackground = true)
fun DetailScreenPreview() {
    DetailScreen(
        onBackPressed = {},
        onBookmarkToggled = {}
    )
}