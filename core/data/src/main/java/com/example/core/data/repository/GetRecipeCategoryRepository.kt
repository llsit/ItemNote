package com.example.core.data.repository

import com.example.core.model.response.RecipeCategoryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetRecipeCategoryRepository {
    suspend fun getCategory(): Flow<com.example.core.model.response.RecipeCategoryResponse>
}

class GetRecipeCategoryRepositoryImpl @Inject constructor(
//    private val apiService: ApiService
) : GetRecipeCategoryRepository {
    override suspend fun getCategory(): Flow<com.example.core.model.response.RecipeCategoryResponse> = flow {
//        val response = apiService.getCategory()
//        if (response.isSuccessful) {
//            response.body()?.let {
//                emit(it)
//            } ?: {
//                error("Get Recipe Category Error")
//            }
//        } else {
//            error("Get Recipe Category Error")
//        }
    }

}