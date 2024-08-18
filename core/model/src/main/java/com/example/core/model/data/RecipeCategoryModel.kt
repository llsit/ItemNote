package com.example.core.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeCategoryModel(
    @SerialName(value = "idCategory")
    val idCategory: String,
    @SerialName(value = "strCategory")
    val strCategory: String,
    @SerialName(value = "strCategoryThumb")
    val strCategoryThumb: String,
    @SerialName(value = "strCategoryDescription")
    val strCategoryDescription: String
)
