package com.example.itemnote.di

import com.example.itemnote.data.repository.ItemRepository
import com.example.itemnote.data.repository.ItemRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {
    @Binds
    fun bindItemRepository(repository: ItemRepositoryImpl): ItemRepository

}