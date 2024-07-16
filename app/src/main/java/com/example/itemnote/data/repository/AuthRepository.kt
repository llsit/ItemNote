package com.example.itemnote.data.repository

import com.example.itemnote.utils.UiState
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface AuthRepository {
    fun loginUser(email: String, password: String): Flow<UiState<AuthResult>>

    fun registerUser(email: String, password: String): Flow<UiState<AuthResult>>

}

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override fun loginUser(email: String, password: String): Flow<UiState<AuthResult>> = flow {
        runCatching {
            emit(value = UiState.Loading)
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
        }.onSuccess { result ->
            emit(value = UiState.Success(data = result))
        }.onFailure {
            emit(value = UiState.Error(it.message.toString()))
        }
    }

    override fun registerUser(email: String, password: String): Flow<UiState<AuthResult>> = flow {
        runCatching {
            emit(value = UiState.Loading)
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        }.onSuccess { result ->
            emit(value = UiState.Success(data = result))
        }.onFailure {
            emit(value = UiState.Error(it.message.toString()))
        }
    }

}