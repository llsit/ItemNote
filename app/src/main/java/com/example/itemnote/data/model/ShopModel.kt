package com.example.itemnote.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ShopModel(
    val id: String = "",
    val name: String = "",
    val date: String = "",
    val location: String = "",
    val price: Double = 0.0
)