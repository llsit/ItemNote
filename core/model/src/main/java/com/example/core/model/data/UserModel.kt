package com.example.core.model.data

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val name: String = "",
    val email: String = ""
)