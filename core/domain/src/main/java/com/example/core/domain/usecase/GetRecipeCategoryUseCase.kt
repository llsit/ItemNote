package com.example.core.domain.usecase

import com.example.core.data.mapper.toCategoryModel
import com.example.core.data.repository.GetRecipeCategoryRepository
import com.example.core.model.data.CategoryModel
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