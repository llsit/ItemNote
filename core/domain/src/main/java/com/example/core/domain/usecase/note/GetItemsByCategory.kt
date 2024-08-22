package com.example.core.domain.usecase.note

import com.example.core.data.repository.ItemRepository
import com.example.core.model.data.ItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetItemsByCategory {
    fun getItemsByCategory(categoryId: String): Flow<List<ItemModel>>
}

class GetItemsByCategoryImpl @Inject constructor(
    private val itemRepository: ItemRepository,
    private val getMinShopUseCase: GetMinShopUseCase
) : GetItemsByCategory {
    override fun getItemsByCategory(categoryId: String): Flow<List<ItemModel>> = flow {
        itemRepository.getItemByCategory(categoryId).collect { result ->
            getMinShopUseCase.getMinShop(result).collect {
                emit(it)
            }
        }
    }

}
