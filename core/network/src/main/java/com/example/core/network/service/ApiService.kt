package com.example.core.network.service

import com.example.core.model.response.RecipeCategoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("categories.php")
    suspend fun getCategory(): Response<RecipeCategoryResponse>
}