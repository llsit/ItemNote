package com.example.recipe.domain

import com.example.recipe.data.mapper.toCategoryModel
import com.example.recipe.data.model.CategoryModel
import com.example.recipe.data.repository.GetRecipeCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetRecipeCategoryUseCase {
    suspend operator fun invoke(): Flow<List<CategoryModel>>
}

class GetRecipeCategoryUseCaseImpl @Inject constructor(
    private val repository: GetRecipeCategoryRepository
) : GetRecipeCategoryUseCase {
    override suspend fun invoke(): Flow<List<CategoryModel>> {
        return repository.getCategory().map {
            it.categories.toCategoryModel()
        }
    }
}