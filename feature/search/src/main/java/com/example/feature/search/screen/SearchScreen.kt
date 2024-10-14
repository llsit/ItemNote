package com.example.feature.search.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.model.data.RecipeInfo
import com.example.design.ui.Loading
import com.example.design.ui.SearchSection
import com.example.feature.search.state.SearchUiState

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            onSearch = { viewModel.search(it) }
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
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(recipes) { recipe ->
                        SearchResultItem(recipe)
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
fun SearchEmpty() {
    Box {
        Text(
            text = "No results found",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun SearchError(message: String) {
    Box {
        Text(text = "Result Not Found", color = MaterialTheme.colorScheme.error)
//        Text(text = "Error: $message", color = MaterialTheme.colorScheme.error)
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