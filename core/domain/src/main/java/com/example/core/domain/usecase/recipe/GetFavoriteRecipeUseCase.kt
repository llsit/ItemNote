package com.example.core.domain.usecase.recipe

import com.example.core.data.repository.RecipeRepository
import com.example.core.model.data.RecipeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
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
                val recipeInfoFlows = idList.map { id ->
                    repository.getRecipeDetail(id)
                }
                if (recipeInfoFlows.isEmpty()) {
                    flowOf(emptyList())
                } else {
                    combine(recipeInfoFlows) { recipeInfoArray ->
                        recipeInfoArray.map {
                            it.copy(isFavorite = true)
                        }.toList()
                    }
                }
            }
    }
}