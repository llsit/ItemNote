package com.example.feature.categorydetail.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.usecase.recipe.GetRecipeByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRecipeByCategoryUseCase: GetRecipeByCategoryUseCase
) : ViewModel() {

    val categoryName = savedStateHandle.get<String>("categoryName")

    fun getRecipes() = viewModelScope.launch {
        categoryName?.let {
            getRecipeByCategoryUseCase.invoke(it)
                .collect {

                }
        }
    }
}