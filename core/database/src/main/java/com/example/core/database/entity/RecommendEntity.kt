package com.example.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecommendEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mealThumb: String,
    val title: String,
    val category: String,
)