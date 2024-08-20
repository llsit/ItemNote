package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.database.entity.RecommendEntity

@Dao
interface RecommendDao {

    @Query("SELECT * FROM RecommendEntity")
    fun getAll(): List<RecommendEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recommend: RecommendEntity)

}