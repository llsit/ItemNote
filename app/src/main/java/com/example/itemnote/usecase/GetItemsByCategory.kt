package com.example.itemnote.usecase

import com.example.itemnote.data.model.ItemModel
import com.example.itemnote.data.repository.ItemRepository
import com.example.itemnote.utils.UiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetItemsByCategory {
    fun getItemsByCategory(categoryId: String): Flow<UiState<List<ItemModel>>>
}

class GetItemsByCategoryImpl @Inject constructor(
    private val itemRepository: ItemRepository
) : GetItemsByCategory {
    override fun getItemsByCategory(categoryId: String): Flow<UiState<List<ItemModel>>> {
        return itemRepository.getItemByCategory(categoryId)
    }

}
