package com.example.core.model.data

import kotlinx.serialization.Serializable

@Serializable
data class ShopModel(
    val id: String = "",
    val name: String = "",
    val date: String = "",
    val location: String = "",
    val price: Double = 0.0
)

fun ShopModel.toMap(): Map<String, Any> {
    return mapOf(
        "id" to id,
        "name" to name,
        "date" to date,
        "location" to location,
        "price" to price
    )
}