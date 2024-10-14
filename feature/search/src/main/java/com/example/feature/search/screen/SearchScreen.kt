package com.example.feature.search.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.model.data.IngredientInfo
import com.example.core.model.data.RecipeInfo
import com.example.design.ui.SearchSection

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
//    val searchQuery by viewModel.searchQuery.collectAsState()
//    val searchResults by viewModel.searchResults.collectAsState()

    val searchResults = listOf(
        RecipeInfo(
            title = "Spaghetti Bolognese",
            category = "Pasta",
            imageUrl = "https://www.example.com/spaghetti.jpg",
            ingredients = listOf(
                IngredientInfo("Spaghetti", "500g"),
                IngredientInfo("Beef", "250g"),
                IngredientInfo("Tomato", "2")
            )
        ),
        RecipeInfo(
            title = "Chicken Caesar Salad",
            category = "Salad",
            imageUrl = "https://www.example.com/salad.jpg",
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            onValueChange = {}
        )

//        if (isLoading) {
//            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
//        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(searchResults) { recipe ->
                SearchResultItem(recipe)
            }
        }
    }
}

@Composable
fun SearchResultItem(recipe: RecipeInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = recipe.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = recipe.category, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SearchScreenPreview() {
    SearchScreen()
}