package com.example.itemnote.usecase

import com.example.itemnote.data.model.ItemModel
import com.example.itemnote.data.repository.AddItemRepository
import com.example.itemnote.utils.UiState
import kotlinx.coroutines.flow.Flow
import java.util.Date
import java.util.UUID
import javax.inject.Inject

interface AddItemUseCase {
    fun addItem(name: String): Flow<UiState<Boolean>>
}

class AddItemUseCaseImpl @Inject constructor(
    private val addItemRepository: AddItemRepository
) : AddItemUseCase {
    override fun addItem(name: String): Flow<UiState<Boolean>> {
        val data = ItemModel(
            id = UUID.randomUUID().toString(),
            name = name,
            date = Date().toString()
        )
        return addItemRepository.addItem(data)
    }

}