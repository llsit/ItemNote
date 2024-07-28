package com.example.itemnote.usecase

import com.example.itemnote.data.model.ShopModel
import com.example.itemnote.data.repository.ShopRepository
import com.example.itemnote.utils.UiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UpdateShopUseCase {
    fun updateShop(itemId: String, shopModel: ShopModel): Flow<UiState<Unit>>
}

class UpdateShopUseCaseImpl @Inject constructor(
    private val repository: ShopRepository
) : UpdateShopUseCase {
    override fun updateShop(itemId: String, shopModel: ShopModel): Flow<UiState<Unit>> {
        return repository.updateShop(itemId, shopModel)
    }

}