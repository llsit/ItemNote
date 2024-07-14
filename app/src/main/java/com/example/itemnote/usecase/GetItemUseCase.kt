package com.example.itemnote.usecase

import com.example.itemnote.data.model.ItemModel
import com.example.itemnote.data.repository.ItemRepository
import com.example.itemnote.utils.UiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetItemUseCase {
    fun getItems(): Flow<UiState<List<ItemModel>>>
}

class GetItemUseCaseImpl @Inject constructor(
    private val itemRepository: ItemRepository
) : GetItemUseCase {
    override fun getItems(): Flow<UiState<List<ItemModel>>> {
        return itemRepository.getItem()
    }

}