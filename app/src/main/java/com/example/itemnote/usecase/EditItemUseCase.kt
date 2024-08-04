package com.example.itemnote.usecase

import com.example.itemnote.data.model.ItemModel
import com.example.itemnote.data.repository.ItemRepository
import com.example.itemnote.utils.UiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface EditItemUseCase {
    fun editItem(
        name: String,
        imageUri: String,
        category: CategoryModel,
        itemModel: ItemModel
    ): Flow<UiState<Unit>>
}

class EditItemUseCaseImpl @Inject constructor(
    private val itemRepository: ItemRepository
) : EditItemUseCase {
    override fun editItem(
        name: String,
        imageUri: String,
        category: CategoryModel,
        itemModel: ItemModel
    ): Flow<UiState<Unit>> {
        val item = itemModel.copy(
            name = name,
            imageUrl = imageUri,
            categoryModel = category
        )
        return itemRepository.editItem(item)
    }

}