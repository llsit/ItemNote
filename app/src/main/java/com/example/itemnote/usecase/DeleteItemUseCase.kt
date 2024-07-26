package com.example.itemnote.usecase

import com.example.itemnote.data.repository.ItemRepository
import com.example.itemnote.utils.UiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DeleteItemUseCase {
    fun deleteItem(shopId: String): Flow<UiState<Unit>>
}

class DeleteItemUseCaseImpl @Inject constructor(
    private val itemRepository: ItemRepository
) : DeleteItemUseCase {
    override fun deleteItem(shopId: String): Flow<UiState<Unit>> {
        return itemRepository.deleteItem(shopId)
    }

}