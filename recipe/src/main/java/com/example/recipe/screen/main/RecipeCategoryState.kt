package com.example.recipe.screen.main

import com.example.recipe.data.model.CategoryModel

sealed class RecipeCategoryState {
    object Idle : RecipeCategoryState()
    object Loading : RecipeCategoryState()
    data class Success(val categories: List<CategoryModel>) : RecipeCategoryState()
    data class Error(val message: String) : RecipeCategoryState()

}