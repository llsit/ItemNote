package com.example.core.domain.usecase.note

import com.example.core.common.utils.UiState
import com.example.core.data.repository.ShopRepository
import com.example.core.model.data.ShopModel
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