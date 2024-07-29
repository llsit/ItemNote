package com.example.itemnote.usecase

import com.example.itemnote.data.model.ItemModel
import com.example.itemnote.data.repository.ItemRepository
import com.example.itemnote.utils.UiState
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
                        getMinShopUseCase.getMinShop(it).collect { result ->
                            emit(result)
                        }
                    }
                }

                else -> emit(result)
            }
        }
    }

}
