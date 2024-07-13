package com.example.itemnote.di

import com.example.itemnote.usecase.AddItemUseCase
import com.example.itemnote.usecase.AddItemUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideAddItemUseCase(useCase: AddItemUseCaseImpl): AddItemUseCase {
        return useCase
    }

}