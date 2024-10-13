package com.example.feature.recipe.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.model.data.RecommendationModel
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
    val recommendRecipes by viewModel.recommendRecipes.collectAsStateWithLifecycle()
    val state by viewModel.stateCategories.collectAsStateWithLifecycle()

    RecipeHomeComponent(
        onNavigateToDetail = {
            navController.navigate("detail/$it")
        },
        onNavigateToCategoryDetail = {
            navController.navigate("categoryList/$it")
        },
        recommendRecipes = recommendRecipes,
        state = state
    )
}

@Composable
fun RecipeHomeComponent(
    onNavigateToDetail: (String) -> Unit = {},
    onNavigateToCategoryDetail: (String) -> Unit = {},
    recommendRecipes: List<RecommendationModel>,
    state: RecipeCategoryState
) {
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
                when (state) {
                    is RecipeCategoryState.Loading -> {
                        Loading()
                    }

                    is RecipeCategoryState.Error -> {
                        Timber.d("Error: ${state.message}")
                    }

                    is RecipeCategoryState.Success -> {
                        FoodCategories(state.categories) {
                            onNavigateToCategoryDetail.invoke(it)
                        }
                    }

                    else -> Unit
                }
            }

            item {
                RecommendationList(
                    recommendRecipes = recommendRecipes,
                    onClick = onNavigateToDetail,
                    onFavoriteClick = {

                    }
                )
            }

//            item {
//                RecipeList()
//            }
        }
    }
}

@Preview
@Composable
fun RecipeHomePreview() {
    RecipeHomeScreen()
}