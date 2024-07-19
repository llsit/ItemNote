package com.example.itemnote.data.repository

import com.example.itemnote.data.model.UserModel
import com.example.itemnote.utils.Constants.Companion.FIREBASE_USERS_COLLECTION
import com.example.itemnote.utils.PreferenceManager
import com.example.itemnote.utils.UiState
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface AuthRepository {
    fun loginUser(email: String, password: String): Flow<UiState<AuthResult>>

    fun registerUser(email: String, password: String): Flow<UiState<Unit>>

    fun addUser(user: UserModel): Flow<UiState<Unit>>

    fun checkUserLogin(): Boolean

    fun saveCurrentUserId(userId: String)
}

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val preferenceManager: PreferenceManager
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

    override fun registerUser(email: String, password: String): Flow<UiState<Unit>> = flow {
        runCatching {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        }.onSuccess { result ->
            emit(value = UiState.Success(data = Unit))
        }.onFailure {
            emit(value = UiState.Error(it.message.toString()))
        }
    }

    override fun addUser(user: UserModel): Flow<UiState<Unit>> = flow {
        runCatching {
            firebaseAuth.currentUser?.let {
                firestore.collection(FIREBASE_USERS_COLLECTION).document(it.uid).set(user)
                saveCurrentUserId(it.uid)
            } ?: {
//                emit(value = UiState.Error("User not found"))
            }
        }.onSuccess {
            emit(value = UiState.Success(Unit))
        }.onFailure {
            emit(value = UiState.Error(it.message.toString()))
        }
    }

    override fun checkUserLogin(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun saveCurrentUserId(userId: String) {
        preferenceManager.saveCurrentUserId(userId)
    }

}