package com.example.itemnote.data.repository

import com.example.itemnote.data.model.ItemModel
import com.example.itemnote.utils.UiState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface AddItemRepository {
    fun addItem(data: ItemModel): Flow<UiState<Boolean>>
}

class AddItemRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : AddItemRepository {
    override fun addItem(data: ItemModel): Flow<UiState<Boolean>> {
        return flow {
            emit(value = UiState.Loading)
//            val result = firestore.collection("items").await()
//            emit(value = Resource.Success(data = result))
        }
    }
}