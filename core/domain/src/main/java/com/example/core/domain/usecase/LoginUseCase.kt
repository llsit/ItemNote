package com.example.core.domain.usecase

import com.example.core.common.utils.UiState
import com.example.core.data.repository.AuthRepository
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