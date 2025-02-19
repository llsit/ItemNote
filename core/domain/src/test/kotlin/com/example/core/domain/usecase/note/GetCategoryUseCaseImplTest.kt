package com.example.core.domain.usecase.note

import com.example.core.data.repository.CategoryRepository
import com.example.core.model.data.CategoryModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class GetCategoryUseCaseImplTest {

    private lateinit var getCategoryUseCase: GetCategoryUseCaseImpl
    private val categoryRepository: CategoryRepository = mockk()

    @Before
    fun setup() {
        getCategoryUseCase = GetCategoryUseCaseImpl(categoryRepository)
    }

    @Test
    fun getCategory_Success() {
        val mockCategories = listOf(CategoryModel("1", "Category 1"), CategoryModel("2", "Category 2"))
        every { categoryRepository.getCategory() } returns flowOf(mockCategories)

        val result = getCategoryUseCase.getCategory()

        assertEquals(mockCategories, result)
        verify { categoryRepository.getCategory() }
    }
}
