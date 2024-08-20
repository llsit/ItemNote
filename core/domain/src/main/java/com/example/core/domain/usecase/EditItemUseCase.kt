package com.example.core.domain.usecase

import com.example.core.data.repository.ItemRepository
import com.example.core.model.data.CategoryModel
import com.example.core.model.data.ItemModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface EditItemUseCase {
    fun editItem(
        name: String,
        imageUri: String,
        category: CategoryModel,
        itemModel: ItemModel
    ): Flow<Unit>
}

class EditItemUseCaseImpl @Inject constructor(
    private val itemRepository: ItemRepository
) : EditItemUseCase {
    override fun editItem(
        name: String,
        imageUri: String,
        category: CategoryModel,
        itemModel: ItemModel
    ): Flow<Unit> {
        val item = itemModel.copy(
            name = name,
            imageUrl = imageUri,
            categoryModel = category
        )
        return itemRepository.editItem(item)
    }

}