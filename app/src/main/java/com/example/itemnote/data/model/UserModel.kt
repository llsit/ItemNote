package com.example.itemnote.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val name: String = "",
    val email: String = ""
)