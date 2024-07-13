package com.example.itemnote.di

import com.example.itemnote.data.repository.AddItemRepository
import com.example.itemnote.data.repository.AddItemRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {
    @Binds
    fun bindAddItemRepository(repository: AddItemRepositoryImpl): AddItemRepository

}