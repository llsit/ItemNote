package com.example.feature.recipe.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.usecase.GetRecipeCategoryUseCase
import com.example.core.model.data.RecipeCategoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeMainViewModel @Inject constructor(
    private val getRecipeCategoryUseCase: GetRecipeCategoryUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<RecipeCategoryState>(RecipeCategoryState.Idle)
    val state = _state.asStateFlow()

    val categories: MutableStateFlow<List<RecipeCategoryModel>> = MutableStateFlow(emptyList())

    init {
        getCategories()
    }

    private fun getCategories() = viewModelScope.launch {
        getRecipeCategoryUseCase.invoke()
            .onStart { _state.value = RecipeCategoryState.Loading }
            .catch { _state.value = RecipeCategoryState.Error(it.message.toString()) }
            .collect { categories.value = it }
    }
}