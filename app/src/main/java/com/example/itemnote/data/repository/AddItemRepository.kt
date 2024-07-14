package com.example.itemnote.data.repository

import com.example.itemnote.data.model.ItemModel
import com.example.itemnote.utils.UiState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface AddItemRepository {
    fun addItem(data: ItemModel): Flow<UiState<Unit>>
}

class AddItemRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : AddItemRepository {
    override fun addItem(data: ItemModel): Flow<UiState<Unit>> {
        return flow {
            try {
                firestore.collection("a").add(data).await()
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}