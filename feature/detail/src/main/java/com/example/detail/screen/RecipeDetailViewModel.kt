package com.example.detail.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.utils.UiState
import com.example.core.data.repository.RecipeRepository
import com.example.core.domain.usecase.recipe.GetRecipeDetailUseCase
import com.example.core.model.data.RecipeInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository,
    private val getRecipeDetailUseCase: GetRecipeDetailUseCase
) : ViewModel() {

    private val recipeId: String = checkNotNull(savedStateHandle["recipeId"])
    private val _state = MutableStateFlow<UiState<RecipeInfo>>(UiState.Idle)
    val state = _state.asStateFlow()
    val recipeInfo: MutableStateFlow<RecipeInfo> = MutableStateFlow(RecipeInfo())

    init {
        getRecipeDetails()
    }

    private fun getRecipeDetails() = viewModelScope.launch {
        getRecipeDetailUseCase.invoke(recipeId)
            .onStart {
                _state.value = UiState.Loading
            }
            .catch {
                _state.value = UiState.Error(it.message.toString())
            }
            .collect {
                recipeInfo.value = it
            }
    }

    fun setFavorite(isFavorite: Boolean) = viewModelScope.launch {
        if (isFavorite) {
            recipeRepository.removeFavorite(recipeId)
                .catch {
                    recipeInfo.value = recipeInfo.value.copy(isFavorite = isFavorite)
                }
                .collect {
                    recipeInfo.value = recipeInfo.value.copy(isFavorite = !isFavorite)
                }
        } else {
            recipeRepository.saveFavorite(recipeId)
                .catch {
                    recipeInfo.value = recipeInfo.value.copy(isFavorite = isFavorite)
                }
                .collect {
                    recipeInfo.value = recipeInfo.value.copy(isFavorite = !isFavorite)
                }
        }
    }

}