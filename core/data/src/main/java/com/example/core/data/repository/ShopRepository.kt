package com.example.core.data.repository

import com.example.core.data.utils.Constants.Firebase.FIREBASE_ITEMS_COLLECTION
import com.example.core.data.utils.Constants.Firebase.FIREBASE_ITEM_COLLECTION
import com.example.core.data.utils.Constants.Firebase.FIREBASE_PRICE_FIELD
import com.example.core.data.utils.Constants.Firebase.FIREBASE_SHOP_COLLECTION
import com.example.core.data.utils.UiState
import com.example.core.model.data.ShopModel
import com.example.core.model.data.toMap
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
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
    private val authRepository: AuthRepository
) : ShopRepository {

    override fun addShop(shop: ShopModel, idItem: String): Flow<UiState<Unit>> = flow {
        runCatching {
            firestore.collection(FIREBASE_ITEMS_COLLECTION)
                .document(authRepository.getUserID() ?: "No User ID")
                .collection(FIREBASE_ITEM_COLLECTION)
                .document(idItem)
                .collection(FIREBASE_SHOP_COLLECTION)
                .document(shop.id)
                .set(shop).await()
        }.onSuccess {
            emit(UiState.Success(Unit))
        }.onFailure {
            emit(UiState.Error(it.message.toString()))
        }
    }

    override fun getShop(idItem: String): Flow<UiState<List<ShopModel>>> = flow {
        runCatching {
            firestore.collection(FIREBASE_ITEMS_COLLECTION)
                .document(authRepository.getUserID() ?: "No User ID")
                .collection(FIREBASE_ITEM_COLLECTION)
                .document(idItem)
                .collection(FIREBASE_SHOP_COLLECTION)
                .orderBy(FIREBASE_PRICE_FIELD, Query.Direction.ASCENDING)
                .get()
                .await().documents.mapNotNull { it.toObject<ShopModel>() }
        }.onSuccess {
            emit(UiState.Success(it))
        }.onFailure {
            emit(UiState.Error(it.message.toString()))
        }
    }

    override fun getMinShop(itemId: String): Flow<UiState<ShopModel>> = flow {
        runCatching {
            firestore.collection(FIREBASE_ITEMS_COLLECTION)
                .document(authRepository.getUserID() ?: "No User ID")
                .collection(FIREBASE_ITEM_COLLECTION)
                .document(itemId)
                .collection(FIREBASE_SHOP_COLLECTION)
                .orderBy(FIREBASE_PRICE_FIELD, Query.Direction.ASCENDING).limit(1)
                .get()
                .await()
                .documents.firstOrNull()
        }.onSuccess {
            emit(UiState.Success(it?.toObject<ShopModel>()))
        }.onFailure {
            emit(UiState.Error(it.message.toString()))
        }
    }

    override fun deleteShop(itemId: String, shopId: String): Flow<UiState<Unit>> = flow {
        runCatching {
            firestore.collection(FIREBASE_ITEMS_COLLECTION)
                .document(authRepository.getUserID() ?: "No User ID")
                .collection(FIREBASE_ITEM_COLLECTION)
                .document(itemId)
                .collection(FIREBASE_SHOP_COLLECTION)
                .document(shopId)
                .delete()
        }.onSuccess {
            emit(UiState.Success(Unit))
        }.onFailure {
            emit(UiState.Error(it.message.toString()))
        }
    }

    override fun updateShop(itemId: String, shopModel: ShopModel): Flow<UiState<Unit>> =
        flow {
            runCatching {
                firestore.collection(FIREBASE_ITEMS_COLLECTION)
                    .document(authRepository.getUserID() ?: "No User ID")
                    .collection(FIREBASE_ITEM_COLLECTION)
                    .document(itemId)
                    .collection(FIREBASE_SHOP_COLLECTION)
                    .document(shopModel.id)
                    .update(shopModel.toMap()).await()
            }.onSuccess {
                emit(UiState.Success(Unit))
            }.onFailure {
                emit(UiState.Error(it.message.toString()))
            }
        }
}