package com.example.core.model.data

data class RecipeInfo(
    val id: String = "",
    val title: String = "",
    val prepTime: String = "",
    val difficulty: String = "",
    val calories: String = "0",
    val description: String = "",
    val ingredients: List<IngredientInfo> = listOf(),
    val isBookmarked: Boolean = false,
    val videoUrl: String? = null,
    val imageUrl: String? = null,
    val rating: Float? = null,
    val reviewCount: Int? = null,
    val tags: String = "",
    val instructions: String = "",
)

data class IngredientInfo(
    val name: String = "",
    val amount: String = "",
)
