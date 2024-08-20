package com.example.core.data.mapper

import com.example.core.common.utils.ResponseMapper
import com.example.core.model.data.RecommendationModel
import com.example.core.model.response.MealResponse

object RecommendationMapper : ResponseMapper<List<RecommendationModel>, List<MealResponse>> {
    override fun asResponse(domain: List<RecommendationModel>): List<MealResponse> {
        return listOf()
    }

    override fun asDomain(entity: List<MealResponse>): List<RecommendationModel> {
        return entity.map {
            RecommendationModel(
                mealThumb = it.strMealThumb.orEmpty(),
                title = it.strMeal.orEmpty(),
                category = it.strCategory.orEmpty()
            )
        }
    }
}

fun List<MealResponse>.toRecommendationModel(): List<RecommendationModel> {
    return RecommendationMapper.asDomain(this)
}