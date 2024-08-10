package com.example.itemnote.usecase

import com.example.itemnote.data.model.ItemModel
import com.example.itemnote.data.repository.ItemRepository
import com.example.itemnote.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetItemUseCase {
    fun getItems(): Flow<UiState<List<ItemModel>>>
}

class GetItemUseCaseImpl @Inject constructor(
    private val itemRepository: ItemRepository,
    private val getMinShopUseCase: GetMinShopUseCase
) : GetItemUseCase {
    override fun getItems(): Flow<UiState<List<ItemModel>>> = flow {
        itemRepository.getItem().collect { result ->
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