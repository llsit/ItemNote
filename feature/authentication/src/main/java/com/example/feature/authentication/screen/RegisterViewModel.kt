package com.example.feature.authentication.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.utils.UiState
import com.example.core.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val name = mutableStateOf("")

    private val _registerState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val registerState: StateFlow<UiState<Unit>> = _registerState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    fun onNameChange(newName: String) {
        name.value = newName
    }

    fun registerUser() = viewModelScope.launch {
        registerUseCase.invoke(name = name.value, email = email.value, password = password.value)
            .collect { result ->
                when (result) {
                    is UiState.Loading -> {
                        _registerState.value = UiState.Loading
                    }

                    is UiState.Success -> {
                        _registerState.value = UiState.Success(Unit)
                    }

                    is UiState.Error -> {
                        _registerState.value = UiState.Error(result.message)
                    }

                    UiState.Idle -> Unit
                }
            }
    }
}