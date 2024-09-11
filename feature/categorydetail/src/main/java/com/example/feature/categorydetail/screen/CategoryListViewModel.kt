package com.example.feature.categorydetail.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.usecase.recipe.GetRecipeByCategoryUseCase
import com.example.core.model.data.RecipeInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRecipeByCategoryUseCase: GetRecipeByCategoryUseCase
) : ViewModel() {

    private val _categoryName = savedStateHandle.get<String>("categoryName") ?: "Category"
    val categoryName = _categoryName

    private val _recipes = MutableStateFlow<List<RecipeInfo>>(emptyList())
    val recipes: StateFlow<List<RecipeInfo>> = _recipes.asStateFlow()


    init {
        getRecipes()
    }

    private fun getRecipes() = viewModelScope.launch {
        _categoryName?.let {
            getRecipeByCategoryUseCase.invoke(it)
                .onStart { }
                .collect {
                    _recipes.value = it
                }
        }
    }
}