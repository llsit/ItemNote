package com.example.core.data.repository

import com.example.core.common.di.IoDispatcher
import com.example.core.common.utils.Constants.Firebase.FIREBASE_CATEGORIES_COLLECTION
import com.example.core.common.utils.Constants.Firebase.FIREBASE_CATEGORY_COLLECTION
import com.example.core.common.utils.PreferenceManager
import com.example.core.model.data.CategoryModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface CategoryRepository {
    fun addCategory(category: CategoryModel): Flow<Unit>

    fun getCategory(): Flow<List<CategoryModel>>
}

class CategoryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val preferenceManager: PreferenceManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CategoryRepository {
    override fun addCategory(category: CategoryModel): Flow<Unit> = callbackFlow {
        firestore.collection(FIREBASE_CATEGORIES_COLLECTION)
            .document(preferenceManager.getUserId() ?: "No User ID")
            .collection(FIREBASE_CATEGORY_COLLECTION)
            .add(category)
            .addOnSuccessListener {
                trySend(Unit)
            }
            .addOnFailureListener {
                close(it)
            }

        awaitClose()
    }.flowOn(ioDispatcher)

    override fun getCategory(): Flow<List<CategoryModel>> = callbackFlow {
        firestore.collection(FIREBASE_CATEGORIES_COLLECTION)
            .document(preferenceManager.getUserId() ?: "No User ID")
            .collection(FIREBASE_CATEGORY_COLLECTION)
            .get()
            .addOnSuccessListener {
                val categories = it.documents.mapNotNull { it.toObject<CategoryModel>() }
                trySend(categories)
            }
            .addOnFailureListener {
                close(it)
            }

        awaitClose()
    }

}