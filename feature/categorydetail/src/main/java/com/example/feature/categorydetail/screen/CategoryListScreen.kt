package com.example.feature.categorydetail.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.core.design.R
import com.example.core.model.data.RecipeInfo
import com.example.design.ui.HeaderLarge
import com.example.design.ui.ToolbarScreen

@Composable
fun CategoryListScreen(
    viewModel: CategoryListViewModel = hiltViewModel(),
    onBackPressed: () -> Unit = {}
) {
    val recipes by viewModel.recipes.collectAsState()
    val categoryName = viewModel.categoryName

    Scaffold(
        topBar = {
            ToolbarScreen(title = "Category", isBack = true) {
                onBackPressed()
            }
        }
    ) { paddingValues ->
        RecipeList(
            recipes = recipes,
            categoryName = categoryName,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        )
    }
}

@Composable
fun RecipeList(
    recipes: List<RecipeInfo>,
    categoryName: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        HeaderLarge(text = categoryName, modifier = Modifier.padding(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ) {
            items(recipes) { recipe ->
                RecipeListItem(recipe.imageUrl.orEmpty(), recipe.title)
            }
        }
    }
}

@Composable
fun RecipeListItem(
    mealThumb: String,
    title: String,
) {
    Column(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable { /* Navigate to recipe details */ }
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
        ) {
            AsyncImage(
                model = mealThumb,
                contentDescription = title,
                placeholder = painterResource(id = R.drawable.placeholder_product),
                error = painterResource(id = R.drawable.placeholder_product),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CategoryListScreenPreview() {
    CategoryListScreen()
}