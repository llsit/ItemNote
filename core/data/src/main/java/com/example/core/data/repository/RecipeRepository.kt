package com.example.core.data.repository

import com.example.core.common.di.IoDispatcher
import com.example.core.common.utils.Constants.Firebase.FIREBASE_FAVORITE_RECIPE_COLLECTION
import com.example.core.common.utils.Constants.Firebase.FIREBASE_RECIPE_COLLECTION
import com.example.core.data.mapper.asRecipeEntity
import com.example.core.data.utils.DataStoreManager
import com.example.core.database.dao.RecipeInfoDao
import com.example.core.database.dao.RecommendDao
import com.example.core.database.entity.mapper.asRecipeInfo
import com.example.core.database.entity.mapper.toMealResponse
import com.example.core.database.entity.mapper.toRecommendEntity
import com.example.core.model.data.RecipeInfo
import com.example.core.model.response.MealResponse
import com.example.core.model.response.RecipeCategoryResponse
import com.example.core.network.service.ApiService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface RecipeRepository {
    suspend fun getCategory(): Flow<RecipeCategoryResponse>
    suspend fun getRecommendRecipe(): Flow<List<MealResponse>>
    suspend fun getRecipeDetail(id: String): Flow<RecipeInfo>
    suspend fun getRecipeByCategory(category: String): Flow<List<MealResponse>>
    suspend fun saveFavorite(recipeId: String): Flow<Unit>
    suspend fun getFavoriteRecipe(): Flow<List<String>>
}

class RecipeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val recommendDao: RecommendDao,
    private val recipeInfoDao: RecipeInfoDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val firestore: FirebaseFirestore,
    private val dataStoreManager: DataStoreManager
) : RecipeRepository {
    override suspend fun getCategory(): Flow<RecipeCategoryResponse> = flow {
        val response = apiService.getCategory()
        if (response.isSuccessful) {
            response.body()?.let {
                emit(it)
            } ?: {
                error("Get Recipe Category Error")
            }
        } else {
            error("Get Recipe Category Error")
        }
    }.flowOn(ioDispatcher)

    override suspend fun getRecommendRecipe(): Flow<List<MealResponse>> = flow {
        val dao = recommendDao.getAll()
        val results = mutableListOf<List<MealResponse>>()
        if (dao.isEmpty()) {
            repeat(10) {
                val response = apiService.getRecommendRecipe()
                if (response.isSuccessful) {
                    response.body()?.let {
                        results.add(it.meals)
                    }
                }
            }
            recommendDao.insertAll(results.toList().flatten().toRecommendEntity())
            emit(results.toList().flatten())
        } else {
            emit(dao.toMealResponse())
        }
    }.flowOn(ioDispatcher)

    override suspend fun getRecipeDetail(id: String): Flow<RecipeInfo> = flow {
        val dao = recipeInfoDao.getRecipeInfoById(id)
        if (dao == null) {
            val response = apiService.getRecipeDetail(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    recipeInfoDao.insertRecipeInfo(it.meals.first().asRecipeEntity())
                    emit(it.meals.first().asRecipeEntity().asRecipeInfo())
                }
            }
        } else {
            emit(dao.asRecipeInfo())
        }
    }.flowOn(ioDispatcher)

    override suspend fun getRecipeByCategory(category: String): Flow<List<MealResponse>> = flow {
        val response = apiService.getRecipeByCategory(category)
        if (response.isSuccessful) {
            response.body()?.let {
                emit(it.meals.toList())
            }
        } else {
            error("Get Recipe By Category Error")
        }
    }.flowOn(ioDispatcher)

    override suspend fun saveFavorite(recipeId: String): Flow<Unit> = callbackFlow {
        firestore.collection(FIREBASE_FAVORITE_RECIPE_COLLECTION)
            .document(dataStoreManager.getUserId().first())
            .collection(FIREBASE_RECIPE_COLLECTION)
            .add(mapOf("id" to recipeId))
            .addOnSuccessListener {
                trySend(Unit)
                close()
            }
            .addOnFailureListener {
                close(it)
            }

        awaitClose()
    }.flowOn(ioDispatcher)

    override suspend fun getFavoriteRecipe(): Flow<List<String>> = callbackFlow {
        firestore.collection(FIREBASE_FAVORITE_RECIPE_COLLECTION)
            .document(dataStoreManager.getUserId().first())
            .collection(FIREBASE_RECIPE_COLLECTION)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val items = snapshot.documents.mapNotNull { it.get("id") as? String }
                    trySend(items)
                }
            }

        awaitClose()
    }.flowOn(ioDispatcher)

}