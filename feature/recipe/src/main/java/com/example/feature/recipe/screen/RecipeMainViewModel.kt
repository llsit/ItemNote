package com.example.feature.recipe.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.usecase.recipe.GetRecipeCategoryUseCase
import com.example.core.domain.usecase.recipe.GetRecommendRecipeUseCase
import com.example.core.model.data.RecipeCategoryModel
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
    private val getRecommendRecipeUseCase: GetRecommendRecipeUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<RecipeCategoryState>(RecipeCategoryState.Idle)
    val state = _state.asStateFlow()

    val categories: MutableStateFlow<List<RecipeCategoryModel>> = MutableStateFlow(emptyList())
    val recommendRecipes: MutableStateFlow<List<RecommendationModel>> =
        MutableStateFlow(emptyList())

    init {
        getCategories()
        getRecommendRecipes()
    }

    private fun getCategories() = viewModelScope.launch {
        getRecipeCategoryUseCase.invoke()
            .onStart { _state.value = RecipeCategoryState.Loading }
            .catch { _state.value = RecipeCategoryState.Error(it.message.toString()) }
            .collect { categories.value = it }
    }

    private fun getRecommendRecipes() = viewModelScope.launch {
        getRecommendRecipeUseCase.invoke()
            .onStart { _state.value = RecipeCategoryState.Loading }
            .catch { _state.value = RecipeCategoryState.Error(it.message.toString()) }
            .collect {
                recommendRecipes.value = it
            }
    }
}