package com.example.itemnote.usecase

import com.example.itemnote.data.model.ItemModel
import com.example.itemnote.data.repository.ItemRepository
import com.example.itemnote.utils.UiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetItemByIdUseCase {
    fun getItemById(itemId: String): Flow<UiState<ItemModel>>
}

class GetItemByIdUseCaseImpl @Inject constructor(
    private val itemRepository: ItemRepository
) : GetItemByIdUseCase {
    override fun getItemById(itemId: String): Flow<UiState<ItemModel>> {
        return itemRepository.getItemById(itemId)
    }

}