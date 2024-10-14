package com.example.core.domain.usecase.recipe

import com.example.core.data.repository.RecipeRepository
import com.example.core.model.data.RecipeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

interface GetFavoriteRecipeUseCase {
    suspend fun invoke(): Flow<List<RecipeInfo>>
}

class GetFavoriteRecipeUseCaseImpl @Inject constructor(
    private val repository: RecipeRepository
) : GetFavoriteRecipeUseCase {
    override suspend fun invoke(): Flow<List<RecipeInfo>> {
        return repository.getFavoriteRecipe()
            .flatMapConcat { idList ->
                val recipeInfoFlows: List<Flow<RecipeInfo>> = idList.map { id ->
                    repository.getRecipeDetail(id)
                }

                combine(recipeInfoFlows) { recipeInfoArray ->
                    recipeInfoArray.toList()
                }
            }
    }
}