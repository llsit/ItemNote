package com.example.itemnote.screen.Authentication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itemnote.usecase.RegisterUseCase
import com.example.itemnote.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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

    fun registerUser(email: String, password: String) = viewModelScope.launch {
        registerUseCase.invoke(email = email, password = password).collectLatest { result ->
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