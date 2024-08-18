package com.example.feature.note.screen.user

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.repository.AuthRepository
import com.example.core.data.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val name = mutableStateOf("Name")
    val email = mutableStateOf("john.doe@example.com")

    init {
        getDataUser()
    }

    private fun getDataUser() = viewModelScope.launch {
        authRepository.getDataUser().collect {
            when (it) {
                is UiState.Error -> Unit
                UiState.Idle -> Unit
                UiState.Loading -> Unit
                is UiState.Success -> {
                    it.data?.let {
                        name.value = it.name
                        email.value = it.email
                    }
                }
            }
        }
    }
}