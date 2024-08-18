package com.example.feature.note.screen.main

import androidx.lifecycle.viewModelScope
import com.example.core.data.repository.AuthRepository
import com.example.core.data.utils.AuthState
import com.example.core.data.utils.Constants.Category.HOME
import com.example.core.data.utils.UiState
import com.example.core.domain.usecase.GetCategoryUseCase
import com.example.core.domain.usecase.GetItemUseCase
import com.example.core.domain.usecase.GetItemsByCategory
import com.example.core.model.data.ItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getItemUseCase: GetItemUseCase,
    private val authRepository: AuthRepository,
    getCategoryUseCase: GetCategoryUseCase,
    private val getItemsByCategory: GetItemsByCategory
) : BaseViewModel(getCategoryUseCase) {

    private val _uiState = MutableStateFlow<UiState<List<ItemModel>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        getCategory()
    }

    private fun getItems() = viewModelScope.launch {
        getItemUseCase.getItems()
            .onStart { _uiState.value = UiState.Loading }
            .collect {
                when (it) {
                    is UiState.Error -> {
                        _uiState.value = UiState.Error(it.message)
                    }

                    UiState.Idle -> Unit
                    UiState.Loading -> _uiState.value = UiState.Loading
                    is UiState.Success -> {
                        _uiState.value = UiState.Success(it.data)

                    }
                }
            }
    }

    fun getItemsByCategory(categoryId: String) = viewModelScope.launch {
        if (categoryId == HOME) {
            getItems()
        } else {
            getItemsByCategory.getItemsByCategory(categoryId)
                .onStart { _uiState.value = UiState.Loading }
                .collect {
                    when (it) {
                        is UiState.Error -> {
                            _uiState.value = UiState.Error(it.message)
                        }

                        UiState.Idle -> Unit
                        UiState.Loading -> _uiState.value = UiState.Loading
                        is UiState.Success -> {
                            _uiState.value = UiState.Success(it.data)
                        }
                    }
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout().collect {
                when (it) {
                    is UiState.Error -> Unit
                    UiState.Idle -> Unit
                    UiState.Loading -> Unit
                    is UiState.Success -> {
                        _authState.value = AuthState.Unauthenticated
                    }
                }
            }
        }
    }
}