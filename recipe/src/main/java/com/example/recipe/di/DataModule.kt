package com.example.recipe.di

import com.example.recipe.data.repository.GetRecipeCategoryRepository
import com.example.recipe.data.repository.GetRecipeCategoryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun bindGetRecipeCategoryRepository(repository: GetRecipeCategoryRepositoryImpl): GetRecipeCategoryRepository
}