package com.example.recipe.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipe.component.BottomNavigationBar
import com.example.recipe.component.FoodCategories
import com.example.recipe.component.HeaderLarge
import com.example.recipe.component.RecipeList
import com.example.recipe.component.RecommendationList
import com.example.recipe.component.SearchSection

@Composable
fun RecipeMainScreen(
    viewModel: RecipeMainViewModel = hiltViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        topBar = {

        },
        bottomBar = {
            BottomNavigationBar()
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                HeaderLarge(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = "What recipe are you looking for?"
                )
            }

            item {
                SearchSection(
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {}
                )
            }

            item {
                FoodCategories(categories)
            }

            item {
                RecommendationList()
            }

            item {
                RecipeList()
            }
        }
    }
}
