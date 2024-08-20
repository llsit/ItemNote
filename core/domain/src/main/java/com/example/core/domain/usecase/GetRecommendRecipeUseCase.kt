package com.example.core.domain.usecase

import com.example.core.data.mapper.toRecommendationModel
import com.example.core.data.repository.RecipeRepository
import com.example.core.model.data.RecommendationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetRecommendRecipeUseCase {
    suspend fun invoke(): Flow<List<RecommendationModel>>
}

class GetRecommendRecipeUseCaseImpl @Inject constructor(
    private val repository: RecipeRepository
) : GetRecommendRecipeUseCase {
    override suspend fun invoke(): Flow<List<RecommendationModel>> {
        return repository.getRecommendRecipe().map {
            it.toRecommendationModel()
        }
    }
}