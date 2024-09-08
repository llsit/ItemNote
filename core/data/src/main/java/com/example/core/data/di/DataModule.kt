package com.example.core.data.di

import com.example.core.common.utils.PreferenceManager
import com.example.core.common.utils.PreferenceManagerImpl
import com.example.core.data.repository.AuthRepository
import com.example.core.data.repository.AuthRepositoryImpl
import com.example.core.data.repository.CategoryRepository
import com.example.core.data.repository.CategoryRepositoryImpl
import com.example.core.data.repository.ItemRepository
import com.example.core.data.repository.ItemRepositoryImpl
import com.example.core.data.repository.RecipeRepository
import com.example.core.data.repository.RecipeRepositoryImpl
import com.example.core.data.repository.ShopRepository
import com.example.core.data.repository.ShopRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun bindGetRecipeRepository(repository: RecipeRepositoryImpl): RecipeRepository

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