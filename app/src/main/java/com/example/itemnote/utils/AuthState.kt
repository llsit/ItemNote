package com.example.itemnote.utils

sealed class AuthState {
    object Initial : AuthState()
    object Loading : AuthState()
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
}