package com.example.recipe.network

import com.example.recipe.data.model.RecipeCategoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("categories.php")
    suspend fun getCategory(): Response<RecipeCategoryResponse>
}