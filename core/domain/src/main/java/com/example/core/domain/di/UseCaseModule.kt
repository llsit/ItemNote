package com.example.core.domain.di

import com.example.core.domain.usecase.note.AddCategoryUseCase
import com.example.core.domain.usecase.note.AddCategoryUseCaseImpl
import com.example.core.domain.usecase.note.AddItemUseCase
import com.example.core.domain.usecase.note.AddItemUseCaseImpl
import com.example.core.domain.usecase.note.AddShopUseCase
import com.example.core.domain.usecase.note.AddShopUseCaseImpl
import com.example.core.domain.usecase.authen.CheckUserLoginUseCase
import com.example.core.domain.usecase.authen.CheckUserLoginUseCaseImpl
import com.example.core.domain.usecase.note.DeleteItemUseCase
import com.example.core.domain.usecase.note.DeleteItemUseCaseImpl
import com.example.core.domain.usecase.note.DeleteShopUseCase
import com.example.core.domain.usecase.note.DeleteShopUseCaseImpl
import com.example.core.domain.usecase.note.EditItemUseCase
import com.example.core.domain.usecase.note.EditItemUseCaseImpl
import com.example.core.domain.usecase.note.GetCategoryUseCase
import com.example.core.domain.usecase.note.GetCategoryUseCaseImpl
import com.example.core.domain.usecase.note.GetItemByIdUseCase
import com.example.core.domain.usecase.note.GetItemByIdUseCaseImpl
import com.example.core.domain.usecase.note.GetItemUseCase
import com.example.core.domain.usecase.note.GetItemUseCaseImpl
import com.example.core.domain.usecase.note.GetItemsByCategory
import com.example.core.domain.usecase.note.GetItemsByCategoryImpl
import com.example.core.domain.usecase.note.GetMinShopUseCase
import com.example.core.domain.usecase.note.GetMinShopUseCaseImpl
import com.example.core.domain.usecase.recipe.GetRecipeCategoryUseCase
import com.example.core.domain.usecase.recipe.GetRecipeCategoryUseCaseImpl
import com.example.core.domain.usecase.recipe.GetRecommendRecipeUseCase
import com.example.core.domain.usecase.recipe.GetRecommendRecipeUseCaseImpl
import com.example.core.domain.usecase.note.GetShopUseCase
import com.example.core.domain.usecase.note.GetShopUseCaseImpl
import com.example.core.domain.usecase.authen.LoginUseCase
import com.example.core.domain.usecase.authen.LoginUseCaseImpl
import com.example.core.domain.usecase.authen.RegisterUseCase
import com.example.core.domain.usecase.authen.RegisterUseCaseImpl
import com.example.core.domain.usecase.note.UpdateShopUseCase
import com.example.core.domain.usecase.note.UpdateShopUseCaseImpl
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