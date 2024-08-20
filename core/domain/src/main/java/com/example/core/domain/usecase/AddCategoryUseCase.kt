package com.example.core.domain.usecase

import com.example.core.data.repository.CategoryRepository
import com.example.core.model.data.CategoryModel
import kotlinx.coroutines.flow.Flow
import java.util.Date
import java.util.UUID
import javax.inject.Inject

interface AddCategoryUseCase {
    fun addCategory(categoryName: String): Flow<Unit>
}

class AddCategoryUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : AddCategoryUseCase {
    override fun addCategory(categoryName: String): Flow<Unit> {
        val category = CategoryModel(
            id = UUID.randomUUID().toString(),
            name = categoryName,
            date = Date().toString()
        )
        return categoryRepository.addCategory(category)
    }

}
