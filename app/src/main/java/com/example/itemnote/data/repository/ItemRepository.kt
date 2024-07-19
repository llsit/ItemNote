package com.example.itemnote.data.repository

import com.example.itemnote.data.model.ItemModel
import com.example.itemnote.utils.Constants.Companion.FIREBASE_ITEMS_COLLECTION
import com.example.itemnote.utils.Constants.Companion.FIREBASE_ITEM_COLLECTION
import com.example.itemnote.utils.PreferenceManager
import com.example.itemnote.utils.UiState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface ItemRepository {
    fun addItem(data: ItemModel): Flow<UiState<Unit>>
    fun getItem(): Flow<UiState<List<ItemModel>>>
}

class ItemRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val preferenceManager: PreferenceManager
) : ItemRepository {
    override fun addItem(data: ItemModel): Flow<UiState<Unit>> {
        return flow {
            runCatching {
                firestore.collection(FIREBASE_ITEMS_COLLECTION)
                    .document(preferenceManager.getUserId() ?: "No User ID")
                    .collection(FIREBASE_ITEM_COLLECTION)
                    .document(data.id)
                    .set(data).await()
            }.onSuccess {
                emit(UiState.Success(Unit))
            }.onFailure { e ->
                emit(UiState.Error(e.message.toString()))
            }
        }
    }

    override fun getItem(): Flow<UiState<List<ItemModel>>> {
        return flow {
            runCatching {
                val snapshot = firestore.collection(FIREBASE_ITEMS_COLLECTION)
                    .document(preferenceManager.getUserId() ?: "No User ID")
                    .collection(FIREBASE_ITEM_COLLECTION)
                    .get().await()
                snapshot.documents.mapNotNull { it.toObject<ItemModel>() }
            }.onSuccess {
                emit(UiState.Success(it))
            }.onFailure { e ->
                emit(UiState.Error(e.message.toString()))
            }
        }
    }
}