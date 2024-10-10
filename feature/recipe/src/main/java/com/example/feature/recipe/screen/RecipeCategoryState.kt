package com.example.feature.recipe.screen

import com.example.core.model.data.RecipeCategoryModel

sealed class RecipeCategoryState {
    object Idle : RecipeCategoryState()
    object Loading : RecipeCategoryState()
    data class Success(val categories: List<RecipeCategoryModel>) : RecipeCategoryState()
    data class Error(val message: String) : RecipeCategoryState()
}