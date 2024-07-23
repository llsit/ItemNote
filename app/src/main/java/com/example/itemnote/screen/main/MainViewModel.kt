package com.example.itemnote.screen.main

import androidx.lifecycle.viewModelScope
import com.example.itemnote.SharedViewModel
import com.example.itemnote.data.model.ItemModel
import com.example.itemnote.data.repository.AuthRepository
import com.example.itemnote.usecase.GetItemUseCase
import com.example.itemnote.utils.AuthState
import com.example.itemnote.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getItemUseCase: GetItemUseCase,
    private val authRepository: AuthRepository
) : SharedViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ItemModel>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun getItems() = viewModelScope.launch {
        getItemUseCase.getItems().collect {
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