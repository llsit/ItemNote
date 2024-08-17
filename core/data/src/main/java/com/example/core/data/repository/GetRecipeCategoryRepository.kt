package com.example.core.data.repository

import com.example.core.model.response.RecipeCategoryResponse
import com.example.core.network.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetRecipeCategoryRepository {
    suspend fun getCategory(): Flow<RecipeCategoryResponse>
}

class GetRecipeCategoryRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : GetRecipeCategoryRepository {
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
    }

}