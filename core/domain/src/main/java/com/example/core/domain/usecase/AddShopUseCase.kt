package com.example.core.domain.usecase

import com.example.core.common.utils.UiState
import com.example.core.data.repository.ShopRepository
import com.example.core.model.data.ShopModel
import kotlinx.coroutines.flow.Flow
import java.util.Date
import java.util.UUID
import javax.inject.Inject

interface AddShopUseCase {
    fun addShop(name: String, location: String, price: String, idItem: String): Flow<UiState<Unit>>
}

class AddShopUseCaseImpl @Inject constructor(
    private val shopRepository: ShopRepository
) : AddShopUseCase {
    override fun addShop(
        name: String,
        location: String,
        price: String,
        idItem: String
    ): Flow<UiState<Unit>> {
        val shop = ShopModel(
            id = UUID.randomUUID().toString(),
            name = name,
            date = Date().toString(),
            location = location,
            price = price.ifEmpty { "0.0" }.toDouble()
        )
        return shopRepository.addShop(shop, idItem)
    }
}