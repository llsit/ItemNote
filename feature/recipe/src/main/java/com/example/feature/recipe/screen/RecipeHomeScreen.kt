package com.example.feature.recipe.screen

import androidx.compose.foundation.clickable
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
import com.example.core.model.navigation.BottomNavigationItem
import com.example.design.ui.HeaderLarge
import com.example.design.ui.Loading
import com.example.design.ui.SearchSection
import com.example.feature.recipe.component.FoodCategories
import com.example.feature.recipe.component.RecommendationList
import timber.log.Timber

@Composable
fun RecipeHomeScreen(
    mainNavController: NavHostController = rememberNavController(),
    viewModel: RecipeMainViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val recommendRecipes by viewModel.recommendRecipes.collectAsStateWithLifecycle()
    val state by viewModel.stateCategories.collectAsStateWithLifecycle()

    RecipeHomeComponent(
        onNavigateToDetail = {
            mainNavController.navigate("detail/$it")
        },
        onNavigateToCategoryDetail = {
            mainNavController.navigate("categoryList/$it")
        },
        recommendRecipes = recommendRecipes,
        state = state,
        onFavoriteClick = viewModel::setFavorite,
        onNavigateToSearch = {
            navController.navigate(BottomNavigationItem.Search.route)
        }
    )
}

@Composable
fun RecipeHomeComponent(
    onNavigateToDetail: (String) -> Unit = {},
    onNavigateToCategoryDetail: (String) -> Unit = {},
    recommendRecipes: List<RecommendationModel>,
    state: RecipeCategoryState,
    onFavoriteClick: (String, Boolean) -> Unit,
    onNavigateToSearch: () -> Unit = {}
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onNavigateToSearch()
                        },
                    onValueChange = {},
                    isEnable = false
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
                    onFavoriteClick = onFavoriteClick
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