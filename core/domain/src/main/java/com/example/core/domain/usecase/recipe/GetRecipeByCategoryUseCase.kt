package com.example.core.domain.usecase.recipe

import com.example.core.data.mapper.toRecipeInfoList
import com.example.core.data.repository.RecipeRepository
import com.example.core.model.data.RecipeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetRecipeByCategoryUseCase {
    suspend fun invoke(category: String): Flow<List<RecipeInfo>>
}

class GetRecipeByCategoryUseCaseImpl @Inject constructor(
    private val recipeRepository: RecipeRepository
) : GetRecipeByCategoryUseCase {
    override suspend fun invoke(category: String): Flow<List<RecipeInfo>> {
        return recipeRepository.getRecipeByCategory(category).map {
            it.toRecipeInfoList()
        }
    }
}