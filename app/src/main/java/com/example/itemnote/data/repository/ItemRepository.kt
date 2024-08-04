package com.example.itemnote.data.repository

import android.net.Uri
import com.example.itemnote.data.model.ItemModel
import com.example.itemnote.data.model.toMap
import com.example.itemnote.utils.Constants.Firebase.FIREBASE_ITEMS_COLLECTION
import com.example.itemnote.utils.Constants.Firebase.FIREBASE_ITEM_COLLECTION
import com.example.itemnote.utils.PreferenceManager
import com.example.itemnote.utils.UiState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface ItemRepository {
    fun addItem(data: ItemModel): Flow<UiState<Unit>>
    fun getItem(): Flow<UiState<List<ItemModel>>>
    fun uploadImage(path: String): Flow<String>
    fun getItemByCategory(categoryId: String): Flow<UiState<List<ItemModel>>>
    fun deleteItem(itemId: String): Flow<UiState<Unit>>
    fun editItem(data: ItemModel): Flow<UiState<Unit>>
}

class ItemRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val preferenceManager: PreferenceManager,
    private val storage: FirebaseStorage
) : ItemRepository {
    override fun addItem(data: ItemModel): Flow<UiState<Unit>> = flow {
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

    override fun getItem(): Flow<UiState<List<ItemModel>>> = flow {
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

    override fun uploadImage(path: String): Flow<String> = flow {
        runCatching {
            val storageRef = storage.reference
            val imageRef =
                storageRef.child("images/${preferenceManager.getUserId()}/${System.currentTimeMillis()}.jpg")
            imageRef.putFile(Uri.parse(path)).await()
            imageRef.downloadUrl.await().toString()
        }.onSuccess {
            emit(it)
        }.onFailure {
            error("Upload Image Error")
        }
    }

    override fun getItemByCategory(categoryId: String): Flow<UiState<List<ItemModel>>> = flow {
        runCatching {
            firestore.collection(FIREBASE_ITEMS_COLLECTION)
                .document(preferenceManager.getUserId() ?: "No User ID")
                .collection(FIREBASE_ITEM_COLLECTION)
                .whereEqualTo("categoryModel.id", categoryId)
                .get()
                .await().documents.mapNotNull { it.toObject<ItemModel>() }
        }.onSuccess {
            emit(UiState.Success(it))
        }.onFailure {
            error("getItemByCategory Error")
        }
    }

    override fun deleteItem(itemId: String): Flow<UiState<Unit>> = flow {
        runCatching {
            firestore.collection(FIREBASE_ITEMS_COLLECTION)
                .document(preferenceManager.getUserId() ?: "No User ID")
                .collection(FIREBASE_ITEM_COLLECTION)
                .document(itemId)
                .delete()
        }.onSuccess {
            emit(UiState.Success(Unit))
        }.onFailure {
            emit(UiState.Error(it.message.toString()))
        }
    }

    override fun editItem(data: ItemModel): Flow<UiState<Unit>> = flow {
        runCatching {
            firestore.collection(FIREBASE_ITEMS_COLLECTION)
                .document(preferenceManager.getUserId() ?: "No User ID")
                .collection(FIREBASE_ITEM_COLLECTION)
                .document(data.id)
                .update(data.toMap()).await()
        }.onSuccess {
            emit(UiState.Success(Unit))
        }.onFailure {
            emit(UiState.Error(it.message.toString()))
        }
    }
}