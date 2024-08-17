package com.example.core.data.di

import com.example.core.data.repository.GetRecipeCategoryRepository
import com.example.core.data.repository.GetRecipeCategoryRepositoryImpl
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