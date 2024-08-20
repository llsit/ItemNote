package com.example.core.domain.usecase

import com.example.core.common.utils.UiState
import com.example.core.data.repository.ItemRepository
import com.example.core.model.data.CategoryModel
import com.example.core.model.data.ItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.util.Date
import java.util.UUID
import javax.inject.Inject

interface AddItemUseCase {
    fun addItem(name: String, imageUri: String, category: CategoryModel): Flow<UiState<Unit>>
}

class AddItemUseCaseImpl @Inject constructor(
    private val itemRepository: ItemRepository
) : AddItemUseCase {
    override fun addItem(
        name: String,
        imageUri: String,
        category: CategoryModel
    ): Flow<UiState<Unit>> = flow {
        try {
            emit(UiState.Loading)
            val data = ItemModel(
                id = UUID.randomUUID().toString(),
                name = name,
                date = Date().toString(),
                categoryModel = category
            )
            val updatedData = if (imageUri.isNotEmpty()) {
                val uploadedImageUri = itemRepository.uploadImage(imageUri).first()
                val updatedData = data.copy(imageUrl = uploadedImageUri)
                updatedData
            } else {
                data
            }
            itemRepository.addItem(updatedData).collect {
                emit(it)
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "Unknown error occurred"))
        }
    }
}