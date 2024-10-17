package com.example.feature.search.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.mapper.toRecipeInfoList
import com.example.core.data.repository.RecipeRepository
import com.example.feature.search.state.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun search(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            recipeRepository.searchRecipe(query)
                .catch {
                    _uiState.value = SearchUiState.Error(it.message ?: "Unknown error")
                }
                .collect { results ->
                    if (results.isEmpty()) {
                        _uiState.value = SearchUiState.Empty
                    } else {
                        _uiState.value = SearchUiState.Success(results.toRecipeInfoList())
                    }
                }
        }
    }

    fun removeFavorite(recipeId: String) {
        viewModelScope.launch {
            recipeRepository.removeFavorite(recipeId).collect {}
        }
    }
}