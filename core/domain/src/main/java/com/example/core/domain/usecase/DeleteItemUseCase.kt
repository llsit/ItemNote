package com.example.core.domain.usecase

import com.example.core.common.utils.UiState
import com.example.core.data.repository.ItemRepository
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