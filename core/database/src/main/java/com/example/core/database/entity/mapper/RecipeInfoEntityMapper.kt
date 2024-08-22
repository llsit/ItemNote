package com.example.core.database.entity.mapper

import com.example.core.database.entity.IngredientEntity
import com.example.core.database.entity.RecipeEntity
import com.example.core.model.data.IngredientInfo
import com.example.core.model.data.RecipeInfo

object RecipeInfoEntityMapper : EntityMapper<RecipeInfo, RecipeEntity> {
    override fun asEntity(domain: RecipeInfo): RecipeEntity {
        return RecipeEntity(
            idMeal = domain.id,
            strMeal = domain.title,
            strMealThumb = domain.imageUrl.orEmpty(),
            strCategory = domain.tags,
            strArea = "",
            strInstructions = domain.instructions,
            strYoutube = domain.videoUrl.orEmpty(),
            listIngredient = domain.ingredients.map {
                IngredientEntity(
                    strIngredient = it.name,
                    strMeasure = it.amount
                )
            }
        )
    }

    override fun asDomain(entity: RecipeEntity): RecipeInfo {
        return RecipeInfo(
            id = entity.idMeal,
            title = entity.strMeal,
            prepTime = "",
            difficulty = "",
            calories = "",
            description = "",
            ingredients = entity.listIngredient.map {
                IngredientInfo(
                    name = it.strIngredient,
                    amount = it.strMeasure
                )
            },
            isBookmarked = false,
            videoUrl = entity.strYoutube,
            imageUrl = entity.strMealThumb,
            rating = null,
            reviewCount = null,
            tags = entity.strCategory,
            instructions = entity.strInstructions
        )
    }
}

fun RecipeInfo.asRecipeEntity(): RecipeEntity {
    return RecipeInfoEntityMapper.asEntity(this)
}

fun RecipeEntity.asRecipeInfo(): RecipeInfo {
    return RecipeInfoEntityMapper.asDomain(this)
}