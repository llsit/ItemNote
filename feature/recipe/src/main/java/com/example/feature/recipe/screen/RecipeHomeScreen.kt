package com.example.feature.recipe.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.design.ui.HeaderLarge
import com.example.design.ui.Loading
import com.example.feature.recipe.component.FoodCategories
import com.example.feature.recipe.component.RecommendationList
import com.example.feature.recipe.component.SearchSection
import timber.log.Timber

@Composable
fun RecipeHomeScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: RecipeMainViewModel = hiltViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    val recommendRecipes by viewModel.recommendRecipes.collectAsState()
    val state by viewModel.state.collectAsState()
    when (state) {
        is RecipeCategoryState.Loading -> {
            Loading()
        }

        is RecipeCategoryState.Error -> {
            val error = state as RecipeCategoryState.Error
            Timber.d("Error: ${error.message}")
        }

        else -> Unit
    }
    Box(
        modifier = Modifier.padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier,
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
                FoodCategories(categories) {
                    navController.navigate("categoryList/${it}")
                }
            }

            item {
                RecommendationList(recommendRecipes) {
                    navController.navigate("detail/${it}")
                }
            }

//            item {
//                RecipeList()
//            }
        }
    }
}