package com.example.itemnote.data.repository

import com.example.itemnote.usecase.CategoryModel
import com.example.itemnote.utils.Constants.Companion.FIREBASE_CATEGORIES_COLLECTION
import com.example.itemnote.utils.Constants.Companion.FIREBASE_CATEGORY_COLLECTION
import com.example.itemnote.utils.PreferenceManager
import com.example.itemnote.utils.UiState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface CategoryRepository {
    fun addCategory(category: CategoryModel): Flow<UiState<Unit>>

    fun getCategory(): Flow<UiState<List<CategoryModel>>>
}

class CategoryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val preferenceManager: PreferenceManager
) : CategoryRepository {
    override fun addCategory(category: CategoryModel): Flow<UiState<Unit>> = flow {
        runCatching {
            firestore.collection(FIREBASE_CATEGORIES_COLLECTION)
                .document(preferenceManager.getUserId() ?: "No User ID")
                .collection(FIREBASE_CATEGORY_COLLECTION)
                .add(category)
                .await()
        }.onSuccess {
            emit(UiState.Success(Unit))
        }.onFailure {
            emit(UiState.Error(it.message.toString()))
        }
    }

    override fun getCategory(): Flow<UiState<List<CategoryModel>>> = flow {
        runCatching {
            firestore.collection(FIREBASE_CATEGORIES_COLLECTION)
                .document(preferenceManager.getUserId() ?: "No User ID")
                .collection(FIREBASE_CATEGORY_COLLECTION)
                .get()
                .await().documents.mapNotNull { it.toObject<CategoryModel>() }
        }.onSuccess {
            emit(UiState.Success(it))
        }.onFailure {
            emit(UiState.Error(it.message.toString()))
        }
    }

}