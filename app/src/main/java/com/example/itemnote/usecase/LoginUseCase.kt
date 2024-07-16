package com.example.itemnote.usecase

import com.example.itemnote.data.repository.AuthRepository
import com.example.itemnote.utils.UiState
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LoginUseCase {
    operator fun invoke(email: String, password: String): Flow<UiState<AuthResult>>
}

class LoginUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : LoginUseCase {
    override fun invoke(email: String, password: String): Flow<UiState<AuthResult>> {
        return authRepository.loginUser(email, password)
    }

}