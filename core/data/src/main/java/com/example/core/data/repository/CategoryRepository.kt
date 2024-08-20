package com.example.core.data.repository

import com.example.core.common.utils.Constants.Firebase.FIREBASE_CATEGORIES_COLLECTION
import com.example.core.common.utils.Constants.Firebase.FIREBASE_CATEGORY_COLLECTION
import com.example.core.model.data.CategoryModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface CategoryRepository {
    fun addCategory(category: CategoryModel): Flow<Unit>

    fun getCategory(): Flow<List<CategoryModel>>
}

class CategoryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val preferenceManager: com.example.core.common.utils.PreferenceManager
) : CategoryRepository {
    override fun addCategory(category: CategoryModel): Flow<Unit> =
        flow {
            runCatching {
                firestore.collection(FIREBASE_CATEGORIES_COLLECTION)
                    .document(preferenceManager.getUserId() ?: "No User ID")
                    .collection(FIREBASE_CATEGORY_COLLECTION)
                    .add(category)
                    .await()
            }.onSuccess {
                emit(Unit)
            }.onFailure {
                error(it.message.toString())
            }
        }

    override fun getCategory(): Flow<List<CategoryModel>> =
        flow {
            runCatching {
                firestore.collection(FIREBASE_CATEGORIES_COLLECTION)
                    .document(preferenceManager.getUserId() ?: "No User ID")
                    .collection(FIREBASE_CATEGORY_COLLECTION)
                    .get()
                    .await().documents.mapNotNull { it.toObject<CategoryModel>() }
            }.onSuccess {
                emit(it)
            }.onFailure {
                error(it.message.toString())
            }
        }

}