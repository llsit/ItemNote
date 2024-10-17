package com.example.feature.search.state

import com.example.core.model.data.RecipeInfo

sealed class SearchUiState {
    object Idle : SearchUiState()
    object Loading : SearchUiState()
    data class Success(val recipes: List<RecipeInfo>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
    object Empty : SearchUiState()
}
