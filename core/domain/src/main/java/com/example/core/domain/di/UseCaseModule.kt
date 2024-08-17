package com.example.core.domain.di

import com.example.core.domain.usecase.GetRecipeCategoryUseCase
import com.example.core.domain.usecase.GetRecipeCategoryUseCaseImpl
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