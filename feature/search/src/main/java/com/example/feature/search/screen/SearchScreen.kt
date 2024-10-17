package com.example.feature.search.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.design.ui.FavoriteRecipeItem
import com.example.design.ui.Loading
import com.example.design.ui.SearchSection
import com.example.feature.search.component.SearchEmpty
import com.example.feature.search.component.SearchError
import com.example.feature.search.state.SearchUiState

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            query = searchQuery,
            onSearch = {
                keyboardController?.hide()
                viewModel.search(it)
            }
        )

        when (uiState) {
            is SearchUiState.Idle -> {
                SearchIdle()
            }

            is SearchUiState.Loading -> {
                Loading()
            }

            is SearchUiState.Success -> {
                val recipes = (uiState as SearchUiState.Success).recipes
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(recipes) { recipe ->
                        FavoriteRecipeItem(
                            title = recipe.title,
                            category = recipe.category,
                            isFavorite = recipe.isFavorite,
                            recipeId = recipe.id,
                            imageUrl = recipe.imageUrl.orEmpty(),
                            isEnableFavorite = false,
                            onClick = {
                                keyboardController?.hide()
                                navController.navigate("detail/${recipe.id}")
                            },
                            onRemove = { viewModel.removeFavorite(it) }
                        )
                    }
                }
            }

            is SearchUiState.Empty -> {
                SearchEmpty()
            }

            is SearchUiState.Error -> {
                val message = (uiState as SearchUiState.Error).message
                SearchError(message)
            }
        }
    }
}

@Composable
fun SearchIdle() {
    Box {
        Text(
            text = "Please enter a search query",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SearchScreenPreview() {
    SearchScreen()
}