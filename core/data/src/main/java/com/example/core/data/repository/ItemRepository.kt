package com.example.core.data.repository

import android.net.Uri
import com.example.core.common.di.IoDispatcher
import com.example.core.common.utils.Constants.Firebase.FIREBASE_ITEMS_COLLECTION
import com.example.core.common.utils.Constants.Firebase.FIREBASE_ITEM_COLLECTION
import com.example.core.common.utils.PreferenceManager
import com.example.core.common.utils.UiState
import com.example.core.model.data.ItemModel
import com.example.core.model.data.toMap
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface ItemRepository {
    fun addItem(data: ItemModel): Flow<Unit>
    fun getItem(): Flow<List<ItemModel>>
    fun getItemById(itemId: String): Flow<UiState<ItemModel>>
    fun uploadImage(path: String): Flow<String>
    fun getItemByCategory(categoryId: String): Flow<List<ItemModel>>
    fun deleteItem(itemId: String): Flow<UiState<Unit>>
    fun editItem(data: ItemModel): Flow<Unit>
}

class ItemRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val preferenceManager: PreferenceManager,
    private val storage: FirebaseStorage,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ItemRepository {
    override fun addItem(data: ItemModel): Flow<Unit> = flow {
        runCatching {
            firestore.collection(FIREBASE_ITEMS_COLLECTION)
                .document(preferenceManager.getUserId() ?: "No User ID")
                .collection(FIREBASE_ITEM_COLLECTION)
                .document(data.id)
                .set(data).await()
        }.onSuccess {
            emit(Unit)
        }.onFailure { e ->
            error(e.message.toString())
        }
    }.flowOn(ioDispatcher)

    override fun getItem(): Flow<List<ItemModel>> = callbackFlow {
        val userId = preferenceManager.getUserId() ?: "No User ID"

        val listenerRegistration = firestore.collection(FIREBASE_ITEMS_COLLECTION)
            .document(userId)
            .collection(FIREBASE_ITEM_COLLECTION)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val items = snapshot.documents.mapNotNull { it.toObject(ItemModel::class.java) }
                    trySend(items)
                }
            }

        awaitClose {
            listenerRegistration.remove()
        }
    }.flowOn(ioDispatcher)

    override fun getItemById(itemId: String): Flow<UiState<ItemModel>> = flow {
        runCatching {
            firestore.collection(FIREBASE_ITEMS_COLLECTION)
                .document(preferenceManager.getUserId() ?: "No User ID")
                .collection(FIREBASE_ITEM_COLLECTION)
                .document(itemId)
                .get().await()
        }.onSuccess {
            emit(UiState.Success(it.toObject<ItemModel>()!!))
        }.onFailure {
            emit(UiState.Error(it.message.toString()))
        }
    }.flowOn(ioDispatcher)

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
    }.flowOn(ioDispatcher)

    override fun getItemByCategory(categoryId: String): Flow<List<ItemModel>> = flow {
        runCatching {
            firestore.collection(FIREBASE_ITEMS_COLLECTION)
                .document(preferenceManager.getUserId() ?: "No User ID")
                .collection(FIREBASE_ITEM_COLLECTION)
                .whereEqualTo("categoryModel.id", categoryId)
                .get()
                .await().documents.mapNotNull { it.toObject<ItemModel>() }
        }.onSuccess {
            emit(it)
        }.onFailure {
            error(it.message.toString())
        }
    }.flowOn(ioDispatcher)

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
    }.flowOn(ioDispatcher)

    override fun editItem(data: ItemModel): Flow<Unit> = flow {
        runCatching {
            firestore.collection(FIREBASE_ITEMS_COLLECTION)
                .document(preferenceManager.getUserId() ?: "No User ID")
                .collection(FIREBASE_ITEM_COLLECTION)
                .document(data.id)
                .update(data.toMap()).await()
        }.onSuccess {
            emit(Unit)
        }.onFailure {
            error(it.message.toString())
        }
    }.flowOn(ioDispatcher)
}