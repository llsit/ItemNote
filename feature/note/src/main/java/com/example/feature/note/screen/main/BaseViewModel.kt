package com.example.feature.note.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.utils.UiState
import com.example.core.domain.usecase.GetCategoryUseCase
import com.example.core.model.data.CategoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryUseCase
) : ViewModel() {

    private val _uiStateCategory = MutableStateFlow<UiState<List<CategoryModel>>>(UiState.Idle)
    val uiStateCategory: StateFlow<UiState<List<CategoryModel>>> = _uiStateCategory.asStateFlow()

    fun getCategory() = viewModelScope.launch {
        getCategoryUseCase.getCategory()
            .onStart { _uiStateCategory.value = UiState.Loading }
            .collect {
                when (it) {
                    is UiState.Error -> _uiStateCategory.value = UiState.Error(it.message)
                    UiState.Idle -> Unit
                    UiState.Loading -> _uiStateCategory.value = UiState.Loading
                    is UiState.Success -> {
                        _uiStateCategory.value = UiState.Success(it.data)
                    }
                }
            }
    }
}