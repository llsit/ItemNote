package com.example.core.domain.usecase

import com.example.core.data.repository.ItemRepository
import com.example.core.data.utils.UiState
import com.example.core.model.data.ItemModel
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