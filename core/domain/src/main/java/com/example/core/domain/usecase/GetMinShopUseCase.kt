package com.example.core.domain.usecase

import com.example.core.common.utils.UiState
import com.example.core.data.repository.ShopRepository
import com.example.core.model.data.ItemModel
import com.example.core.model.data.ShopModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetMinShopUseCase {
    fun getMinShop(items: List<ItemModel>): Flow<List<ItemModel>>
}

class GetMinShopUseCaseImpl @Inject constructor(
    private val shopRepository: ShopRepository
) : GetMinShopUseCase {
    override fun getMinShop(items: List<ItemModel>): Flow<List<ItemModel>> =
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

                emit(itemsWithShops)
            } catch (e: Exception) {
                error(e.message ?: "Unknown error occurred")
            }
        }
}