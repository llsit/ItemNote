package com.example.core.domain.usecase.recipe

import com.example.core.data.repository.RecipeRepository
import com.example.core.model.data.RecipeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

interface GetRecipeDetailUseCase {
    suspend fun invoke(recipeId: String): Flow<RecipeInfo>
}

class GetRecipeDetailUseCaseImpl @Inject constructor(
    private val repository: RecipeRepository
) : GetRecipeDetailUseCase {
    override suspend fun invoke(recipeId: String): Flow<RecipeInfo> {
        return repository.getRecipeDetail(recipeId)
            .combine(repository.getFavoriteRecipe()) { recipe, favorite ->
                recipe.copy(isFavorite = favorite.contains(recipe.id))
            }
    }
}