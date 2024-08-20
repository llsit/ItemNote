package com.example.core.domain.di

import com.example.core.domain.usecase.AddCategoryUseCase
import com.example.core.domain.usecase.AddCategoryUseCaseImpl
import com.example.core.domain.usecase.AddItemUseCase
import com.example.core.domain.usecase.AddItemUseCaseImpl
import com.example.core.domain.usecase.AddShopUseCase
import com.example.core.domain.usecase.AddShopUseCaseImpl
import com.example.core.domain.usecase.CheckUserLoginUseCase
import com.example.core.domain.usecase.CheckUserLoginUseCaseImpl
import com.example.core.domain.usecase.DeleteItemUseCase
import com.example.core.domain.usecase.DeleteItemUseCaseImpl
import com.example.core.domain.usecase.DeleteShopUseCase
import com.example.core.domain.usecase.DeleteShopUseCaseImpl
import com.example.core.domain.usecase.EditItemUseCase
import com.example.core.domain.usecase.EditItemUseCaseImpl
import com.example.core.domain.usecase.GetCategoryUseCase
import com.example.core.domain.usecase.GetCategoryUseCaseImpl
import com.example.core.domain.usecase.GetItemByIdUseCase
import com.example.core.domain.usecase.GetItemByIdUseCaseImpl
import com.example.core.domain.usecase.GetItemUseCase
import com.example.core.domain.usecase.GetItemUseCaseImpl
import com.example.core.domain.usecase.GetItemsByCategory
import com.example.core.domain.usecase.GetItemsByCategoryImpl
import com.example.core.domain.usecase.GetMinShopUseCase
import com.example.core.domain.usecase.GetMinShopUseCaseImpl
import com.example.core.domain.usecase.GetRecipeCategoryUseCase
import com.example.core.domain.usecase.GetRecipeCategoryUseCaseImpl
import com.example.core.domain.usecase.GetRecommendRecipeUseCase
import com.example.core.domain.usecase.GetRecommendRecipeUseCaseImpl
import com.example.core.domain.usecase.GetShopUseCase
import com.example.core.domain.usecase.GetShopUseCaseImpl
import com.example.core.domain.usecase.LoginUseCase
import com.example.core.domain.usecase.LoginUseCaseImpl
import com.example.core.domain.usecase.RegisterUseCase
import com.example.core.domain.usecase.RegisterUseCaseImpl
import com.example.core.domain.usecase.UpdateShopUseCase
import com.example.core.domain.usecase.UpdateShopUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetRecipeCategoryUseCase(useCase: GetRecipeCategoryUseCaseImpl): GetRecipeCategoryUseCase {
        return useCase
    }

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

    @Provides
    fun provideGetShopUseCase(useCase: GetShopUseCaseImpl): GetShopUseCase {
        return useCase
    }

    @Provides
    fun provideRegisterUseCase(useCase: RegisterUseCaseImpl): RegisterUseCase {
        return useCase
    }

    @Provides
    fun provideLoginUseCase(useCase: LoginUseCaseImpl): LoginUseCase {
        return useCase
    }

    @Provides
    fun provideCheckUserLoginUseCase(useCase: CheckUserLoginUseCaseImpl): CheckUserLoginUseCase {
        return useCase
    }

    @Provides
    fun provideAddCategoryUseCase(useCase: AddCategoryUseCaseImpl): AddCategoryUseCase {
        return useCase
    }

    @Provides
    fun provideGetCategoryUseCase(useCase: GetCategoryUseCaseImpl): GetCategoryUseCase {
        return useCase
    }

    @Provides
    fun provideGetItemsByCategoryUseCase(useCase: GetItemsByCategoryImpl): GetItemsByCategory {
        return useCase
    }

    @Provides
    fun provideDeleteShopUseCase(useCase: DeleteShopUseCaseImpl): DeleteShopUseCase {
        return useCase
    }

    @Provides
    fun provideDeleteItemUseCase(useCase: DeleteItemUseCaseImpl): DeleteItemUseCase {
        return useCase
    }

    @Provides
    fun provideUpdateShopUseCase(useCase: UpdateShopUseCaseImpl): UpdateShopUseCase {
        return useCase
    }

    @Provides
    fun provideGetMinShopUseCase(useCase: GetMinShopUseCaseImpl): GetMinShopUseCase {
        return useCase
    }

    @Provides
    fun provideEditItemUseCase(useCase: EditItemUseCaseImpl): EditItemUseCase {
        return useCase
    }

    @Provides
    fun provideGetItemByIdUseCase(useCase: GetItemByIdUseCaseImpl): GetItemByIdUseCase {
        return useCase
    }

    @Provides
    fun provideGetRecommendRecipeUseCase(useCase: GetRecommendRecipeUseCaseImpl): GetRecommendRecipeUseCase {
        return useCase
    }

}