package com.example.itemnote.usecase

import com.example.itemnote.data.repository.CategoryRepository
import com.example.itemnote.utils.UiState
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
