package com.example.core.model.data

data class RecipeInfo(
    val id: String = "",
    val title: String = "",
    val category: String = "",
    val area: String = "",
    val ingredients: List<IngredientInfo> = listOf(),
    val isBookmarked: Boolean = false,
    val videoUrl: String? = null,
    val imageUrl: String? = null,
    val instructions: String = "",
)

data class IngredientInfo(
    val name: String = "",
    val amount: String = "",
)
