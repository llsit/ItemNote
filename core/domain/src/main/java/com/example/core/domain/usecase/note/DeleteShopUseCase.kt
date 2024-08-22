package com.example.core.domain.usecase.note

import com.example.core.data.repository.ShopRepository
import com.example.core.common.utils.UiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DeleteShopUseCase {
    fun deleteShop(itemId: String, shopId: String): Flow<UiState<Unit>>
}

class DeleteShopUseCaseImpl @Inject constructor(
    private val shopRepository: ShopRepository
) : DeleteShopUseCase {
    override fun deleteShop(itemId: String, shopId: String): Flow<UiState<Unit>> {
        return shopRepository.deleteShop(itemId, shopId)
    }

}