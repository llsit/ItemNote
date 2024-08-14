package com.example.recipe.di

import com.example.recipe.domain.GetRecipeCategoryUseCase
import com.example.recipe.domain.GetRecipeCategoryUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetCategoryUseCase(useCase: GetRecipeCategoryUseCaseImpl): GetRecipeCategoryUseCase {
        return useCase
    }
}