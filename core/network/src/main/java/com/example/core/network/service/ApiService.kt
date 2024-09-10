package com.example.core.network.service

import com.example.core.model.response.RecipeCategoryResponse
import com.example.core.model.response.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("categories.php")
    suspend fun getCategory(): Response<RecipeCategoryResponse>

    @GET("random.php")
    suspend fun getRecommendRecipe(): Response<RecipeResponse>

    @GET("lookup.php")
    suspend fun getRecipeDetail(@Query("i") id: String): Response<RecipeResponse>

    @GET("filter.php")
    suspend fun getRecipeByCategory(@Query("c") category: String): Response<RecipeResponse>

}