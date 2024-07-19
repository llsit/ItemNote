package com.example.itemnote.data.repository

import com.example.itemnote.data.model.ShopModel
import com.example.itemnote.utils.Constants.Companion.FIREBASE_ITEMS_COLLECTION
import com.example.itemnote.utils.Constants.Companion.FIREBASE_ITEM_COLLECTION
import com.example.itemnote.utils.Constants.Companion.FIREBASE_SHOP_COLLECTION
import com.example.itemnote.utils.PreferenceManager
import com.example.itemnote.utils.UiState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface ShopRepository {
    fun addShop(shop: ShopModel, idItem: String): Flow<UiState<Unit>>
    fun getShop(idItem: String): Flow<UiState<List<ShopModel>>>
}

class ShopRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val preferenceManager: PreferenceManager
) : ShopRepository {

    override fun addShop(shop: ShopModel, idItem: String): Flow<UiState<Unit>> = flow {
        runCatching {
            firestore.collection(FIREBASE_ITEMS_COLLECTION)
                .document(preferenceManager.getUserId() ?: "No User ID")
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
                .document(preferenceManager.getUserId() ?: "No User ID")
                .collection(FIREBASE_ITEM_COLLECTION)
                .document(idItem)
                .collection(FIREBASE_SHOP_COLLECTION)
                .get()
                .await().documents.mapNotNull { it.toObject<ShopModel>() }
        }.onSuccess {
            emit(UiState.Success(it))
        }.onFailure {
            emit(UiState.Error(it.message.toString()))
        }
    }
}