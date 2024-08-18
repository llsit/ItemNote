package com.example.core.domain.usecase

import com.example.core.data.repository.ShopRepository
import com.example.core.data.utils.UiState
import com.example.core.model.data.ShopModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetShopUseCase {
    fun getShop(idItem: String): Flow<UiState<List<ShopModel>>>
}

class GetShopUseCaseImpl @Inject constructor(
    private val shopRepository: ShopRepository
) : GetShopUseCase {
    override fun getShop(idItem: String): Flow<UiState<List<ShopModel>>> {
        return shopRepository.getShop(idItem)
    }

}