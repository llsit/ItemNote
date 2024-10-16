package com.example.core.domain.usecase.note

import android.content.Context
import com.example.core.data.repository.ItemRepository
import com.example.core.model.data.CategoryModel
import com.example.core.model.data.ItemModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.Date
import java.util.UUID
import javax.inject.Inject

interface AddItemUseCase {
    fun addItem(name: String, imageUri: String, category: CategoryModel): Flow<Unit>
}

class AddItemUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val itemRepository: ItemRepository
) : AddItemUseCase {
    override fun addItem(
        name: String,
        imageUri: String,
        category: CategoryModel
    ): Flow<Unit> = flow {
        try {
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
            Timber.e(e.message)
            error(e.message ?: "Unknown error occurred")
        }
    }
}