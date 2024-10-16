package com.example.feature.note.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.utils.UiState
import com.example.core.domain.usecase.note.GetCategoryUseCase
import com.example.core.model.data.CategoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryUseCase
) : ViewModel() {

    private val _uiStateCategory = MutableStateFlow<UiState<List<CategoryModel>>>(UiState.Idle)
    val uiStateCategory: StateFlow<UiState<List<CategoryModel>>> = _uiStateCategory.asStateFlow()

    private val _uiNoInternet = MutableStateFlow(false)
    val uiNoInternet: StateFlow<Boolean> = _uiNoInternet.asStateFlow()

    fun getCategory() = viewModelScope.launch {
        getCategoryUseCase.getCategory()
            .onStart { _uiStateCategory.value = UiState.Loading }
            .catch { _uiStateCategory.value = UiState.Error(it.message.toString()) }
            .collect {
                _uiStateCategory.value = UiState.Success(it)
            }
    }

    fun onNoInternet() {
        _uiNoInternet.value = true
    }

    fun updateNoInternet() {
        _uiNoInternet.value = false
    }
}