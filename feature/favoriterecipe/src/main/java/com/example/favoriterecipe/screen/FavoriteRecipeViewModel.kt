package com.example.favoriterecipe.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.repository.RecipeRepository
import com.example.core.domain.usecase.recipe.GetFavoriteRecipeUseCase
import com.example.core.model.data.RecipeInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteRecipeViewModel @Inject constructor(
    private val getFavoriteRecipeUseCase: GetFavoriteRecipeUseCase,
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _favoriteRecipes = MutableStateFlow(emptyList<RecipeInfo>())
    val favoriteRecipes: StateFlow<List<RecipeInfo>> = _favoriteRecipes
        .onStart {
            getFavoriteRecipe()
        }.stateIn(
            scope = viewModelScope,
            initialValue = emptyList(),
            started = WhileSubscribed(5000)
        )

    private fun getFavoriteRecipe() {
        viewModelScope.launch {
            getFavoriteRecipeUseCase.invoke().collect {
                _favoriteRecipes.value = it
            }
        }
    }

    fun removeFavoriteRecipe(id: String) {
        viewModelScope.launch {
            recipeRepository.removeFavorite(id).collect {}
        }
    }
}