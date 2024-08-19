package com.example.feature.recipe.screen

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.design.ui.ToolbarScreen
import com.example.feature.recipe.component.BottomNavigationBar
import com.example.feature.recipe.component.FoodCategories
import com.example.feature.recipe.component.HeaderLarge
import com.example.feature.recipe.component.RecipeList
import com.example.feature.recipe.component.RecommendationList
import com.example.feature.recipe.component.SearchSection

@Composable
fun RecipeMainScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: RecipeMainViewModel = hiltViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            ToolbarScreen(
                title = "",
                isBack = true,
                onManuClick = {
                    navController.navigateUp()
                }
            )
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
