package com.example.core.data.repository

import com.example.core.common.di.IoDispatcher
import com.example.core.common.utils.Constants.Firebase.FIREBASE_ITEMS_COLLECTION
import com.example.core.common.utils.Constants.Firebase.FIREBASE_ITEM_COLLECTION
import com.example.core.common.utils.Constants.Firebase.FIREBASE_PRICE_FIELD
import com.example.core.common.utils.Constants.Firebase.FIREBASE_SHOP_COLLECTION
import com.example.core.common.utils.UiState
import com.example.core.model.data.ShopModel
import com.example.core.model.data.toMap
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface ShopRepository {
    fun addShop(shop: ShopModel, idItem: String): Flow<UiState<Unit>>
    fun getShop(idItem: String): Flow<UiState<List<ShopModel>>>
    fun getMinShop(itemId: String): Flow<UiState<ShopModel>>
    fun deleteShop(itemId: String, shopId: String): Flow<UiState<Unit>>
    fun updateShop(itemId: String, shopModel: ShopModel): Flow<UiState<Unit>>
}

class ShopRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ShopRepository {

    override fun addShop(shop: ShopModel, idItem: String): Flow<UiState<Unit>> = callbackFlow {
        firestore.collection(FIREBASE_ITEMS_COLLECTION)
            .document(authRepository.getUserID() ?: "No User ID")
            .collection(FIREBASE_ITEM_COLLECTION)
            .document(idItem)
            .collection(FIREBASE_SHOP_COLLECTION)
            .document(shop.id)
            .set(shop)
            .addOnSuccessListener {
                trySend(UiState.Success(Unit))
            }
            .addOnFailureListener {
                trySend(UiState.Error(it.message.toString()))
            }

        awaitClose()
    }.flowOn(ioDispatcher)

    override fun getShop(idItem: String): Flow<UiState<List<ShopModel>>> = callbackFlow {
        firestore.collection(FIREBASE_ITEMS_COLLECTION)
            .document(authRepository.getUserID() ?: "No User ID")
            .collection(FIREBASE_ITEM_COLLECTION)
            .document(idItem)
            .collection(FIREBASE_SHOP_COLLECTION)
            .orderBy(FIREBASE_PRICE_FIELD, Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                val item = it.documents.mapNotNull { it.toObject<ShopModel>() }
                trySend(UiState.Success(item))
            }
            .addOnFailureListener {
                trySend(UiState.Error(it.message.toString()))
            }

        awaitClose()
    }.flowOn(ioDispatcher)

    override fun getMinShop(itemId: String): Flow<UiState<ShopModel>> = callbackFlow {
        firestore.collection(FIREBASE_ITEMS_COLLECTION)
            .document(authRepository.getUserID() ?: "No User ID")
            .collection(FIREBASE_ITEM_COLLECTION)
            .document(itemId)
            .collection(FIREBASE_SHOP_COLLECTION)
            .orderBy(FIREBASE_PRICE_FIELD, Query.Direction.ASCENDING).limit(1)
            .get()
            .addOnSuccessListener {
                val item = it.documents.firstOrNull()?.toObject<ShopModel>()
                trySend(UiState.Success(item))
            }
            .addOnFailureListener {
                trySend(UiState.Error(it.message.toString()))
            }

        awaitClose()
    }.flowOn(ioDispatcher)

    override fun deleteShop(itemId: String, shopId: String): Flow<UiState<Unit>> = callbackFlow {
        firestore.collection(FIREBASE_ITEMS_COLLECTION)
            .document(authRepository.getUserID() ?: "No User ID")
            .collection(FIREBASE_ITEM_COLLECTION)
            .document(itemId)
            .collection(FIREBASE_SHOP_COLLECTION)
            .document(shopId)
            .delete()
            .addOnSuccessListener {
                trySend(UiState.Success(Unit))
            }
            .addOnFailureListener {
                trySend(UiState.Error(it.message.toString()))
            }

        awaitClose()
    }.flowOn(ioDispatcher)

    override fun updateShop(itemId: String, shopModel: ShopModel): Flow<UiState<Unit>> =
        callbackFlow {
            firestore.collection(FIREBASE_ITEMS_COLLECTION)
                .document(authRepository.getUserID() ?: "No User ID")
                .collection(FIREBASE_ITEM_COLLECTION)
                .document(itemId)
                .collection(FIREBASE_SHOP_COLLECTION)
                .document(shopModel.id)
                .update(shopModel.toMap())
                .addOnSuccessListener {
                    trySend(UiState.Success(Unit))
                }
                .addOnFailureListener {
                    trySend(UiState.Error(it.message.toString()))
                }
            awaitClose()
        }.flowOn(ioDispatcher)
}