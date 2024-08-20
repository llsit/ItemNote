package com.example.core.network.service

import com.example.core.model.response.RecipeCategoryResponse
import com.example.core.model.response.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("categories.php")
    suspend fun getCategory(): Response<RecipeCategoryResponse>

    @GET("random.php")
    suspend fun getRecommendRecipe(): Response<RecipeResponse>

}