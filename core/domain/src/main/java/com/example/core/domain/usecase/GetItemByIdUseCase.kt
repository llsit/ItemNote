package com.example.core.domain.usecase

import com.example.core.data.repository.ItemRepository
import com.example.core.common.utils.UiState
import com.example.core.model.data.ItemModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetItemByIdUseCase {
    fun getItemById(itemId: String): Flow<com.example.core.common.utils.UiState<ItemModel>>
}

class GetItemByIdUseCaseImpl @Inject constructor(
    private val itemRepository: ItemRepository
) : GetItemByIdUseCase {
    override fun getItemById(itemId: String): Flow<com.example.core.common.utils.UiState<ItemModel>> {
        return itemRepository.getItemById(itemId)
    }

}