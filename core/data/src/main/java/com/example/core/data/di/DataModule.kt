package com.example.core.data.di

import com.example.core.data.repository.AuthRepository
import com.example.core.data.repository.AuthRepositoryImpl
import com.example.core.data.repository.CategoryRepository
import com.example.core.data.repository.CategoryRepositoryImpl
import com.example.core.data.repository.GetRecipeCategoryRepository
import com.example.core.data.repository.GetRecipeCategoryRepositoryImpl
import com.example.core.data.repository.ItemRepository
import com.example.core.data.repository.ItemRepositoryImpl
import com.example.core.data.repository.ShopRepository
import com.example.core.data.repository.ShopRepositoryImpl
import com.example.core.data.utils.PreferenceManager
import com.example.core.data.utils.PreferenceManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun bindGetRecipeCategoryRepository(repository: GetRecipeCategoryRepositoryImpl): GetRecipeCategoryRepository

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