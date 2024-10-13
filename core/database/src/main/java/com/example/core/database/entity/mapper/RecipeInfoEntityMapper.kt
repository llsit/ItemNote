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
            strCategory = domain.category,
            strArea = domain.area,
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
            category = entity.strCategory,
            area = entity.strArea,
            ingredients = entity.listIngredient.map {
                IngredientInfo(
                    name = it.strIngredient,
                    amount = it.strMeasure
                )
            },
            isFavorite = false,
            videoUrl = entity.strYoutube,
            imageUrl = entity.strMealThumb,
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