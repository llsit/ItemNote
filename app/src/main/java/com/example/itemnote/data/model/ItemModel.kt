package com.example.itemnote.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ItemModel(
    val id: String = "",
    val name: String = "",
    val date: String = ""
)