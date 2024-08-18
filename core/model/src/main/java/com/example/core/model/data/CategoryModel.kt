package com.example.core.model.data

import kotlinx.serialization.Serializable

@Serializable
data class CategoryModel(
    val id: String = "",
    val name: String = "",
    val date: String = ""
)