package com.example.itemnote.usecase

import com.example.itemnote.data.model.ItemModel
import com.example.itemnote.data.model.ShopModel
import com.example.itemnote.data.repository.ShopRepository
import com.example.itemnote.utils.UiState
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetMinShopUseCase {
    fun getMinShop(result: List<ItemModel>): Flow<UiState<List<ItemModel>>>
}

class GetMinShopUseCaseImpl @Inject constructor(
    private val shopRepository: ShopRepository
) : GetMinShopUseCase {
    override fun getMinShop(items: List<ItemModel>): Flow<UiState<List<ItemModel>>> =
        flow {
            try {
                val itemsWithShops = items.map { item ->
                    coroutineScope {
                        async {
                            val shopResult = shopRepository.getMinShop(item.id).first()
                            when (shopResult) {
                                is UiState.Success -> item.copy(
                                    shop = shopResult.data ?: ShopModel()
                                )

                                else -> item // Keep original item if shop fetch fails
                            }
                        }
                    }
                }.awaitAll()

                emit(UiState.Success(itemsWithShops))
            } catch (e: Exception) {
                emit(UiState.Error(e.message ?: "Unknown error occurred"))
            }
        }
}