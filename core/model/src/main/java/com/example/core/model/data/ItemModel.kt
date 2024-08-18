package com.example.core.model.data

import kotlinx.serialization.Serializable

@Serializable
data class ItemModel(
    val id: String = "",
    val name: String = "",
    val date: String = "",
    val imageUrl: String = "",
    val shop: ShopModel? = null,
    val categoryModel: CategoryModel? = null
)

fun ItemModel.toMap() = mapOf(
    "name" to name,
    "imageUrl" to imageUrl,
    "categoryModel" to categoryModel
)