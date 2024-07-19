package com.example.itemnote.usecase

import com.example.itemnote.data.model.UserModel
import com.example.itemnote.data.repository.AuthRepository
import com.example.itemnote.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

interface RegisterUseCase {
    operator fun invoke(email: String, password: String, name: String): Flow<UiState<Unit>>
}

class RegisterUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : RegisterUseCase {
    override fun invoke(email: String, password: String, name: String): Flow<UiState<Unit>> {
        return repository.registerUser(email, password).flatMapLatest { result ->
            if (result is UiState.Success) {
                val model = UserModel(name, email)
                repository.addUser(model)
            } else {
                flowOf(result)
            }
        }
    }
}