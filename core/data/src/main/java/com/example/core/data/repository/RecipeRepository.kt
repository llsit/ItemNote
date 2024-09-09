package com.example.core.data.repository

import com.example.core.common.di.IoDispatcher
import com.example.core.data.mapper.asRecipeEntity
import com.example.core.database.dao.RecipeInfoDao
import com.example.core.database.dao.RecommendDao
import com.example.core.database.entity.mapper.asRecipeInfo
import com.example.core.database.entity.mapper.toMealResponse
import com.example.core.database.entity.mapper.toRecommendEntity
import com.example.core.model.data.RecipeInfo
import com.example.core.model.response.MealResponse
import com.example.core.model.response.RecipeCategoryResponse
import com.example.core.network.service.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface RecipeRepository {
    suspend fun getCategory(): Flow<RecipeCategoryResponse>
    suspend fun getRecommendRecipe(): Flow<List<MealResponse>>
    suspend fun getRecipeDetail(id: String): Flow<RecipeInfo>
}

class RecipeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val recommendDao: RecommendDao,
    private val recipeInfoDao: RecipeInfoDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
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

}