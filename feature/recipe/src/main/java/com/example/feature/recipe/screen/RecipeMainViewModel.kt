package com.example.feature.recipe.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.repository.RecipeRepository
import com.example.core.domain.usecase.recipe.GetRecipeCategoryUseCase
import com.example.core.domain.usecase.recipe.GetRecommendRecipeUseCase
import com.example.core.domain.usecase.recipe.SaveFavoriteUseCase
import com.example.core.model.data.RecommendationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeMainViewModel @Inject constructor(
    private val getRecipeCategoryUseCase: GetRecipeCategoryUseCase,
    private val getRecommendRecipeUseCase: GetRecommendRecipeUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    private val _stateCategories = MutableStateFlow<RecipeCategoryState>(RecipeCategoryState.Idle)
    val stateCategories = _stateCategories.asStateFlow()

    val recommendRecipes: MutableStateFlow<List<RecommendationModel>> =
        MutableStateFlow(emptyList())

    init {
        getCategories()
        getRecommendRecipes()
    }

    private fun getCategories() = viewModelScope.launch {
        getRecipeCategoryUseCase.invoke()
            .onStart { _stateCategories.value = RecipeCategoryState.Loading }
            .catch { _stateCategories.value = RecipeCategoryState.Error(it.message.toString()) }
            .collect { _stateCategories.value = RecipeCategoryState.Success(it) }
    }

    private fun getRecommendRecipes() = viewModelScope.launch {
        getRecommendRecipeUseCase.invoke()
            .onStart { _stateCategories.value = RecipeCategoryState.Loading }
            .catch { _stateCategories.value = RecipeCategoryState.Error(it.message.toString()) }
            .collect {
                recommendRecipes.value = it
            }
    }

    fun setFavorite(recipeId: String, isFavorite: Boolean) = viewModelScope.launch {
        if (isFavorite) {
            recipeRepository.removeFavorite(recipeId).collect {}
        } else {
            saveFavoriteUseCase.invoke(recipeId).collect {}
        }
    }
}