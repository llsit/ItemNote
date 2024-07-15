package com.example.itemnote.di

import com.example.itemnote.usecase.AddItemUseCase
import com.example.itemnote.usecase.AddItemUseCaseImpl
import com.example.itemnote.usecase.AddShopUseCase
import com.example.itemnote.usecase.AddShopUseCaseImpl
import com.example.itemnote.usecase.GetItemUseCase
import com.example.itemnote.usecase.GetItemUseCaseImpl
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

    @Provides
    fun provideGetItemUseCase(useCase: GetItemUseCaseImpl): GetItemUseCase {
        return useCase
    }

    @Provides
    fun provideAddShopUseCase(useCase: AddShopUseCaseImpl): AddShopUseCase {
        return useCase
    }

}