package com.example.core.database.converter

import androidx.room.TypeConverter
import com.example.core.database.entity.IngredientEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IngredientsConverter {

    private val gson = Gson()

    // Convert List<IngredientEntity> to String
    @TypeConverter
    fun fromIngredientList(ingredients: List<IngredientEntity>): String {
        return gson.toJson(ingredients)
    }

    // Convert String back to List<IngredientEntity>
    @TypeConverter
    fun toIngredientList(data: String): List<IngredientEntity> {
        val listType = object : TypeToken<List<IngredientEntity>>() {}.type
        return gson.fromJson(data, listType)
    }
}
