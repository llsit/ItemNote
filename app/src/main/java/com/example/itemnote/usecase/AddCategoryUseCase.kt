package com.example.itemnote.usecase

import com.example.itemnote.data.repository.CategoryRepository
import com.example.itemnote.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import java.util.Date
import java.util.UUID
import javax.inject.Inject

interface AddCategoryUseCase {
    fun addCategory(categoryName: String): Flow<UiState<Unit>>
}

class AddCategoryUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : AddCategoryUseCase {
    override fun addCategory(categoryName: String): Flow<UiState<Unit>> {
        val category = CategoryModel(
            id = UUID.randomUUID().toString(),
            name = categoryName,
            date = Date().toString()
        )
        return categoryRepository.addCategory(category)
    }

}

@Serializable
data class CategoryModel(
    val id: String = "",
    val name: String = "",
    val date: String = ""
)