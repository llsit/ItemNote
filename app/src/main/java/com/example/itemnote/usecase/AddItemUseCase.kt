package com.example.itemnote.usecase

import com.example.itemnote.data.model.ItemModel
import com.example.itemnote.data.repository.ItemRepository
import com.example.itemnote.utils.UiState
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