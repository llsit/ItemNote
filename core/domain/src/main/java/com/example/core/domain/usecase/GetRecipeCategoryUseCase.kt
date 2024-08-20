package com.example.core.domain.usecase

import com.example.core.data.mapper.toCategoryModel
import com.example.core.data.repository.RecipeRepository
import com.example.core.model.data.RecipeCategoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetRecipeCategoryUseCase {
    suspend operator fun invoke(): Flow<List<RecipeCategoryModel>>
}

class GetRecipeCategoryUseCaseImpl @Inject constructor(
    private val repository: RecipeRepository
) : GetRecipeCategoryUseCase {
    override suspend fun invoke(): Flow<List<RecipeCategoryModel>> {
        return repository.getCategory().map {
            it.categories.toCategoryModel()
        }
    }
}