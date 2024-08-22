package com.example.detail.screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.utils.UiState
import com.example.core.data.repository.RecipeRepository
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
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    val recipeId: String = checkNotNull(savedStateHandle["recipeId"])
    private val _state = MutableStateFlow<UiState<RecipeInfo>>(UiState.Idle)
    val state = _state.asStateFlow()
    val recipeInfo: MutableStateFlow<RecipeInfo> = MutableStateFlow(RecipeInfo())

    init {
        getRecipeDetails()
    }

    private fun getRecipeDetails() = viewModelScope.launch {
        recipeRepository.getRecipeDetail(recipeId)
            .onStart {
                _state.value = UiState.Loading
            }
            .catch {
                Log.d("NutZa", "Error: ${it.message}")
                _state.value = UiState.Error(it.message.toString())
            }
            .collect {
                Log.d("NutZa", "Recipe Detail: $it")
                recipeInfo.value = it
            }
    }

}