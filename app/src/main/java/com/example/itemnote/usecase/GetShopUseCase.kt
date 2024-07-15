package com.example.itemnote.usecase

import com.example.itemnote.data.model.ShopModel
import com.example.itemnote.data.repository.ShopRepository
import com.example.itemnote.utils.UiState
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