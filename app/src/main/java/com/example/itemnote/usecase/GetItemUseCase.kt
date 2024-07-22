package com.example.itemnote.usecase

import com.example.itemnote.data.model.ItemModel
import com.example.itemnote.data.model.ShopModel
import com.example.itemnote.data.repository.ItemRepository
import com.example.itemnote.data.repository.ShopRepository
import com.example.itemnote.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

interface GetItemUseCase {
    fun getItems(): Flow<UiState<List<ItemModel>>>
}

class GetItemUseCaseImpl @Inject constructor(
    private val itemRepository: ItemRepository,
    private val shopRepository: ShopRepository
) : GetItemUseCase {
    override fun getItems(): Flow<UiState<List<ItemModel>>> {
        return itemRepository.getItem().flatMapLatest { result ->
            when (result) {
                is UiState.Success -> {
                    val itemList = result.data
                    flow {
                        try {
                            val itemsWithShops = itemList?.map { item ->
                                (shopRepository.getMinShop(item.id)
                                    .first() as UiState.Success).data.let {
                                    item.copy(shop = it ?: ShopModel())
                                }
                            }
                            emit(UiState.Success(itemsWithShops))
                        } catch (e: Exception) {
                            emit(UiState.Error(e.message.toString()))
                        }
                    }
                }

                else -> {
                    flowOf(result)
                }
            }
        }
    }

}