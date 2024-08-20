package com.example.core.domain.usecase

import com.example.core.common.utils.UiState
import com.example.core.data.repository.CategoryRepository
import com.example.core.model.data.CategoryModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetCategoryUseCase {
    fun getCategory(): Flow<UiState<List<CategoryModel>>>
}

class GetCategoryUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : GetCategoryUseCase {
    override fun getCategory(): Flow<UiState<List<CategoryModel>>> {
        return categoryRepository.getCategory()
    }
}
