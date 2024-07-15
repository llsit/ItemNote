package com.example.itemnote.usecase

import com.example.itemnote.data.model.ShopModel
import com.example.itemnote.data.repository.ShopRepository
import com.example.itemnote.utils.UiState
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
            price = price
        )
        return shopRepository.addItem(shop, idItem)
    }
}