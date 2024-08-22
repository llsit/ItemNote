package com.example.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.database.converter.IngredientsConverter
import com.example.core.database.dao.RecipeInfoDao
import com.example.core.database.dao.RecommendDao
import com.example.core.database.entity.RecipeEntity
import com.example.core.database.entity.RecommendEntity

@Database(
    entities = [RecommendEntity::class, RecipeEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(IngredientsConverter::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recommendDao(): RecommendDao
    abstract fun recipeInfoDao(): RecipeInfoDao
}