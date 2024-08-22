package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.database.entity.RecipeEntity

@Dao
interface RecipeInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeInfo(recipeEntity: RecipeEntity)

    @Query("SELECT * FROM RecipeEntity WHERE :id = idMeal")
    suspend fun getRecipeInfoById(id: String): RecipeEntity?
}