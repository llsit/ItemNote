package com.example.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.core.database.converter.IngredientsConverter

@Entity
@TypeConverters(IngredientsConverter::class)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = false)
    val idMeal: String = "",
    val strMeal: String,
    val strMealThumb: String,
    val strCategory: String,
    val strArea: String,
    val strInstructions: String,
    val strYoutube: String,
    val listIngredient: List<IngredientEntity> = emptyList()
)

@Entity
data class IngredientEntity(
    val strIngredient: String,
    val strMeasure: String
)