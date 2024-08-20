package com.example.core.domain.usecase

import com.example.core.data.repository.ItemRepository
import com.example.core.model.data.ItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetItemUseCase {
    fun getItems(): Flow<List<ItemModel>>
}

class GetItemUseCaseImpl @Inject constructor(
    private val itemRepository: ItemRepository,
    private val getMinShopUseCase: GetMinShopUseCase
) : GetItemUseCase {
    override fun getItems(): Flow<List<ItemModel>> = flow {
        itemRepository.getItem().collect { result ->
            getMinShopUseCase.getMinShop(result).collect {
                emit(it)
            }
        }
    }

}