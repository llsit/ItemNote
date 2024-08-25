package com.example.detail.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Map
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
import coil.compose.AsyncImage
import com.example.core.design.R
import com.example.core.model.data.IngredientInfo
import com.example.core.model.data.RecipeInfo
import com.example.detail.component.IngredientItem
import com.example.detail.component.TextInstructions

@Composable
fun DetailScreen(
    viewModel: RecipeDetailViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
) {
    val recipeInfo by viewModel.recipeInfo.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
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
//                actions = {
//                    IconButton(onClick = onBookmarkToggled) {
//                        Icon(
//                            if (recipeInfo.isBookmarked.or(false)) Icons.Filled.Bookmark else Icons.Outlined.Bookmark,
//                            contentDescription = "Bookmark",
//                            tint = if (recipeInfo!!.isBookmarked) Color.Red else Color.Black
//                        )
//                    }
//                }
            )
        }
    ) { padding ->
        RecipeDetailContent(
            recipeInfo = recipeInfo,
            padding = padding,
//            onVideoClicked = { }
        )
    }
}

@Composable
fun RecipeDetailContent(
    recipeInfo: RecipeInfo,
    padding: PaddingValues,
//    onVideoClicked: (String) -> Unit
) {
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
            AsyncImage(
                model = recipeInfo.imageUrl,
                contentDescription = recipeInfo.title,
                error = painterResource(R.drawable.placeholder_product),
                placeholder = painterResource(R.drawable.placeholder_product),
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
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Category,
                    contentDescription = "icon category",
                    modifier = Modifier.padding(horizontal = 4.dp),
                )
                Text(
                    recipeInfo.category,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Gray,
                )
                Icon(
                    imageVector = Icons.Filled.Map,
                    contentDescription = "icon area",
                    modifier = Modifier.padding(horizontal = 4.dp),
                )
                Text(
                    recipeInfo.area,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    color = Color.Gray,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            TextInstructions(instructions = recipeInfo.instructions)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Ingredients",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
            recipeInfo.ingredients.forEach { ingredient ->
                IngredientItem(ingredient)
            }

//            Button(
//                onClick = { onVideoClicked(recipeInfo.videoUrl.orEmpty()) },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Watch Videos")
//            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DetailScreenPreview() {
    RecipeDetailContent(
        recipeInfo = RecipeInfo(
            id = "1",
            title = "Sample Recipe",
            category = "Category",
            area = "Area",
            ingredients = listOf(
                IngredientInfo("Ingredient 1", "100g"),
                IngredientInfo("Ingredient 2", "200g")
            ),
            isBookmarked = false,
            videoUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
            imageUrl = "https://www.themealdb.com/images/media/meals/1548772327.jpg",
            instructions = "Instructions for the recipe"
        ),
        padding = PaddingValues(16.dp),
//        onVideoClicked = {}
    )
}