package com.example.core.database.di

import android.app.Application
import androidx.room.Room
import com.example.core.database.RecipeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application
    ): RecipeDatabase {
        return Room
            .databaseBuilder(application, RecipeDatabase::class.java, "Recipe.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideRecommendDao(database: RecipeDatabase) = database.recommendDao()

    @Provides
    @Singleton
    fun provideRecipeInfoDao(database: RecipeDatabase) = database.recipeInfoDao()
}