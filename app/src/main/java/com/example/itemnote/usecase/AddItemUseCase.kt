package com.example.itemnote.usecase

import com.example.itemnote.data.model.ItemModel
import com.example.itemnote.data.repository.ItemRepository
import com.example.itemnote.utils.UiState
import kotlinx.coroutines.flow.Flow
import java.util.Date
import java.util.UUID
import javax.inject.Inject

interface AddItemUseCase {
    fun addItem(name: String): Flow<UiState<Unit>>
}

class AddItemUseCaseImpl @Inject constructor(
    private val itemRepository: ItemRepository
) : AddItemUseCase {
    override fun addItem(name: String): Flow<UiState<Unit>> {
        val data = ItemModel(
            id = UUID.randomUUID().toString(),
            name = name,
            date = Date().toString()
        )
        return itemRepository.addItem(data)
    }

}