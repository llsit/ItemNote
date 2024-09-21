package com.example.core.data.repository

import android.net.Uri
import com.example.core.common.di.IoDispatcher
import com.example.core.common.utils.Constants.Firebase.FIREBASE_ITEMS_COLLECTION
import com.example.core.common.utils.Constants.Firebase.FIREBASE_ITEM_COLLECTION
import com.example.core.common.utils.UiState
import com.example.core.data.utils.DataStoreManager
import com.example.core.model.data.ItemModel
import com.example.core.model.data.toMap
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
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
    private val dataStoreManager: DataStoreManager,
    private val storage: FirebaseStorage,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ItemRepository {
    override fun addItem(data: ItemModel): Flow<Unit> = callbackFlow {
        firestore.collection(FIREBASE_ITEMS_COLLECTION)
            .document(dataStoreManager.getUserId().first())
            .collection(FIREBASE_ITEM_COLLECTION)
            .document(data.id)
            .set(data)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(Unit)
                } else {
                    close(it.exception ?: Exception("Error Occurred"))
                }
                close()
            }

        awaitClose()
    }.flowOn(ioDispatcher)

    override fun getItem(): Flow<List<ItemModel>> = callbackFlow {
        val listenerRegistration = firestore.collection(FIREBASE_ITEMS_COLLECTION)
            .document(dataStoreManager.getUserId().first())
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

    override fun getItemById(itemId: String): Flow<UiState<ItemModel>> = callbackFlow {
        firestore.collection(FIREBASE_ITEMS_COLLECTION)
            .document(dataStoreManager.getUserId().first())
            .collection(FIREBASE_ITEM_COLLECTION)
            .document(itemId)
            .get()
            .addOnSuccessListener { task ->
                if (task.exists()) {
                    val item = task.toObject<ItemModel>()
                    trySend(UiState.Success(item))
                } else {
                    trySend(UiState.Error("Item not found"))
                }
                close()
            }.addOnFailureListener {
                trySend(UiState.Error(it.message.toString()))
                close()
            }

        awaitClose()
    }.flowOn(ioDispatcher)

    override fun uploadImage(path: String): Flow<String> = callbackFlow {
        val storageRef = storage.reference
        val imageRef =
            storageRef.child(
                "images/${
                    dataStoreManager.getUserId().first()
                }/${System.currentTimeMillis()}.jpg"
            )
        val uploadImage = imageRef.putFile(Uri.parse(path))
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                    trySend(it.toString())
                    close()
                }.addOnFailureListener { exception ->
                    close(Exception("Failed to get download URL: ${exception.message}"))
                }
            }.addOnFailureListener { exception ->
                close(Exception("Upload Image Error: ${exception.message}"))
            }

        awaitClose { uploadImage.cancel() }
    }.flowOn(ioDispatcher)

    override fun getItemByCategory(categoryId: String): Flow<List<ItemModel>> = callbackFlow {
        firestore.collection(FIREBASE_ITEMS_COLLECTION)
            .document(dataStoreManager.getUserId().first())
            .collection(FIREBASE_ITEM_COLLECTION)
            .whereEqualTo("categoryModel.id", categoryId)
            .get()
            .addOnSuccessListener { task ->
                val items = task.documents.mapNotNull { it.toObject(ItemModel::class.java) }
                trySend(items)
                close()
            }.addOnFailureListener { exception ->
                close(Exception("Failed to get items by category: ${exception.message}"))
            }

        awaitClose()
    }.flowOn(ioDispatcher)

    override fun deleteItem(itemId: String): Flow<UiState<Unit>> = callbackFlow {
        firestore.collection(FIREBASE_ITEMS_COLLECTION)
            .document(dataStoreManager.getUserId().first())
            .collection(FIREBASE_ITEM_COLLECTION)
            .document(itemId)
            .delete()
            .addOnSuccessListener {
                trySend(UiState.Success(Unit)).isSuccess
                close()
            }
            .addOnFailureListener { exception ->
                trySend(UiState.Error(exception.message.toString()))
                close(exception)
            }

        awaitClose()
    }.flowOn(ioDispatcher)

    override fun editItem(data: ItemModel): Flow<Unit> = callbackFlow {
        firestore.collection(FIREBASE_ITEMS_COLLECTION)
            .document(dataStoreManager.getUserId().first())
            .collection(FIREBASE_ITEM_COLLECTION)
            .document(data.id)
            .update(data.toMap())
            .addOnSuccessListener {
                trySend(Unit)
                close()
            }
            .addOnFailureListener { exception ->
                close(Exception(exception.message))
            }

        awaitClose()
    }.flowOn(ioDispatcher)
}