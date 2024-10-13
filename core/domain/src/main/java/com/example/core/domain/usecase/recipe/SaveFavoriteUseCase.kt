package com.example.core.domain.usecase.recipe

import com.example.core.data.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SaveFavoriteUseCase {
    suspend fun invoke(recipeId: String): Flow<Unit>
}

class SaveFavoriteUseCaseImpl @Inject constructor(
    private val recipeRepository: RecipeRepository
) : SaveFavoriteUseCase {
    override suspend fun invoke(recipeId: String): Flow<Unit> {
        return recipeRepository.saveFavorite(recipeId)
    }
}