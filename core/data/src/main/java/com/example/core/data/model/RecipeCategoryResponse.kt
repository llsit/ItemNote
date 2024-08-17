package com.example.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeCategoryResponse(
    @SerialName(value = "categories") val categories: List<Category>
)

@Serializable
data class Category(
    @SerialName(value = "idCategory") val idCategory: String,
    @SerialName(value = "strCategory") val strCategory: String,
    @SerialName(value = "strCategoryThumb") val strCategoryThumb: String,
    @SerialName(value = "strCategoryDescription") val strCategoryDescription: String,
)
