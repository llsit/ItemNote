package com.example.itemnote.screen.authentication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itemnote.usecase.LoginUseCase
import com.example.itemnote.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    val username = mutableStateOf("")
    val password = mutableStateOf("")

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun onUsernameChange(newUsername: String) {
        username.value = newUsername
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    fun login() = viewModelScope.launch {
        loginUseCase.invoke(username.value, password.value).collect {
            when (it) {
                is UiState.Error -> {
                    _uiState.value = UiState.Error(it.message)
                }

                UiState.Idle -> Unit
                UiState.Loading -> {
                    _uiState.value = UiState.Loading
                }

                is UiState.Success -> {
                    _uiState.value = UiState.Success(Unit)
                }
            }
        }
    }
}