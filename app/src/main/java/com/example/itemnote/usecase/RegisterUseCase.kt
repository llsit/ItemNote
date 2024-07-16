package com.example.itemnote.usecase

import com.example.itemnote.data.repository.AuthRepository
import com.example.itemnote.utils.UiState
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RegisterUseCase {
    operator fun invoke(email: String, password: String): Flow<UiState<AuthResult>>
}

class RegisterUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : RegisterUseCase {
    override fun invoke(email: String, password: String): Flow<UiState<AuthResult>> {
        return repository.registerUser(email, password)
    }
}