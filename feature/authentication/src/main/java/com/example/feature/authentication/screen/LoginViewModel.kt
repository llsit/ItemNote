package com.example.feature.authentication.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.utils.AuthState
import com.example.core.common.utils.UiState
import com.example.core.domain.usecase.CheckUserLoginUseCase
import com.example.core.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val checkUserLoginUseCase: CheckUserLoginUseCase
) : ViewModel() {
    val username = mutableStateOf("")
    val password = mutableStateOf("")

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiState = _uiState.asStateFlow()
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun onUsernameChange(newUsername: String) {
        username.value = newUsername
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val isLoggedIn = checkUserLoginUseCase.invoke()
            _authState.value = if (isLoggedIn) {
                AuthState.Authenticated
            } else {
                AuthState.Unauthenticated
            }
        }
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