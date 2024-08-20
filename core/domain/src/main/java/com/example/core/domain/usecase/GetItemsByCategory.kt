package com.example.core.domain.usecase

import com.example.core.common.utils.UiState
import com.example.core.data.repository.ItemRepository
import com.example.core.model.data.ItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetItemsByCategory {
    fun getItemsByCategory(categoryId: String): Flow<UiState<List<ItemModel>>>
}

class GetItemsByCategoryImpl @Inject constructor(
    private val itemRepository: ItemRepository,
    private val getMinShopUseCase: GetMinShopUseCase
) : GetItemsByCategory {
    override fun getItemsByCategory(categoryId: String): Flow<UiState<List<ItemModel>>> = flow {
        itemRepository.getItemByCategory(categoryId).collect { result ->
            when (result) {
                is UiState.Success -> {
                    result.data?.let {
                        getMinShopUseCase.getMinShop(it).collect {
                            emit(it)
                        }
                    }
                }

                else -> emit(result)
            }
        }
    }

}
