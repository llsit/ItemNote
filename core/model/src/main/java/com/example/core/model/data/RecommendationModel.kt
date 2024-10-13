package com.example.core.model.data

data class RecommendationModel(
    val id: String,
    val mealThumb: String,
    val title: String,
    val category: String,
    val isFavorite: Boolean
)