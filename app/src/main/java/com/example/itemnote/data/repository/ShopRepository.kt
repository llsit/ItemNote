package com.example.itemnote.data.repository

import com.example.itemnote.data.model.ShopModel
import com.example.itemnote.utils.Constants.Companion.FIREBASE_SHOP_COLLECTION
import com.example.itemnote.utils.UiState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface ShopRepository {
    fun addItem(shop: ShopModel, idItem: String): Flow<UiState<Unit>>
}

class ShopRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ShopRepository {

    override fun addItem(shop: ShopModel, idItem: String): Flow<UiState<Unit>> = flow {
        runCatching {
            val ref = firestore.collection("a")
                .document(idItem)
            ref.collection(FIREBASE_SHOP_COLLECTION)
                .document(ref.id)
                .set(shop.copy(ref.id)).await()
        }.onSuccess {
            emit(UiState.Success(Unit))
        }.onFailure {
            emit(UiState.Error(it.message.toString()))
        }
    }
}