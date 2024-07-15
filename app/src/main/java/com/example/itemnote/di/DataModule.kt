package com.example.itemnote.di

import com.example.itemnote.data.repository.ItemRepository
import com.example.itemnote.data.repository.ItemRepositoryImpl
import com.example.itemnote.data.repository.ShopRepository
import com.example.itemnote.data.repository.ShopRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {
    @Binds
    fun bindItemRepository(repository: ItemRepositoryImpl): ItemRepository

    @Binds
    fun bindShopRepository(repository: ShopRepositoryImpl): ShopRepository

}