package com.example.itemnote.di

import com.example.itemnote.data.repository.AuthRepository
import com.example.itemnote.data.repository.AuthRepositoryImpl
import com.example.itemnote.data.repository.CategoryRepository
import com.example.itemnote.data.repository.CategoryRepositoryImpl
import com.example.itemnote.data.repository.ItemRepository
import com.example.itemnote.data.repository.ItemRepositoryImpl
import com.example.itemnote.data.repository.ShopRepository
import com.example.itemnote.data.repository.ShopRepositoryImpl
import com.example.itemnote.utils.PreferenceManager
import com.example.itemnote.utils.PreferenceManagerImpl
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

    @Binds
    fun bindAuthRepository(repository: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindPreferenceManager(manager: PreferenceManagerImpl): PreferenceManager

    @Binds
    fun bindCategoryRepository(repository: CategoryRepositoryImpl): CategoryRepository
}