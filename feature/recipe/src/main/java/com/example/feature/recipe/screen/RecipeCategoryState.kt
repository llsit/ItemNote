package com.example.feature.recipe.screen

import com.example.core.model.data.CategoryModel

sealed class RecipeCategoryState {
    object Idle : RecipeCategoryState()
    object Loading : RecipeCategoryState()
    data class Success(val categories: List<CategoryModel>) : RecipeCategoryState()
    data class Error(val message: String) : RecipeCategoryState()

}