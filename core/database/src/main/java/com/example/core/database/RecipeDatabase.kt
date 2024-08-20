package com.example.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.database.dao.RecommendDao
import com.example.core.database.entity.RecommendEntity

@Database(entities = [RecommendEntity::class], version = 1, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recommendDao(): RecommendDao

}