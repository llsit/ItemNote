package com.example.core.data.mapper

import com.example.core.database.entity.mapper.EntityMapper
import com.example.core.model.data.RecipeInfo
import com.example.core.model.response.MealResponse

object ResponseRecipeInfoMapper : EntityMapper<List<MealResponse>, List<RecipeInfo>> {
    override fun asEntity(domain: List<MealResponse>): List<RecipeInfo> {
        return domain.map {
            RecipeInfo(
                id = it.idMeal.orEmpty(),
                title = it.strMeal.orEmpty(),
                imageUrl = it.strMealThumb.orEmpty(),
                category = it.strCategory.orEmpty(),
                area = it.strArea.orEmpty(),
                instructions = it.strInstructions.orEmpty(),
                videoUrl = it.strYoutube.orEmpty(),
                ingredients = listOf()
            )
        }
    }

    override fun asDomain(entity: List<RecipeInfo>): List<MealResponse> {
        return entity.map {
            MealResponse(
                idMeal = it.id,
                strMeal = it.title,
                strMealThumb = it.imageUrl.orEmpty(),
                strCategory = it.category,
                strArea = it.area,
                strInstructions = it.instructions,
                strYoutube = it.videoUrl.orEmpty(),
                strTags = "",
                strDrinkAlternate = "",
            )
        }
    }
}

fun List<MealResponse>.toRecipeInfoList(): List<RecipeInfo> {
    return ResponseRecipeInfoMapper.asEntity(this)
}

fun List<RecipeInfo>.toMealResponseList(): List<MealResponse> {
    return ResponseRecipeInfoMapper.asDomain(this)
}