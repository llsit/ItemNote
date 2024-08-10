package com.example.itemnote.utils

import androidx.navigation.NavController

class NavResultHandler<T>(
    private val navController: NavController,
    private val key: String
) {
    fun getResult(): T? {
        return navController.currentBackStackEntry?.savedStateHandle?.get<T>(key)
    }

    fun removeResult() {
        navController.currentBackStackEntry?.savedStateHandle?.remove<T>(key)
    }

    fun setResult(result: T) {
        navController.previousBackStackEntry?.savedStateHandle?.set(key, result)
    }

    fun consumeResult(onResult: (T) -> Unit) {
        getResult()?.let { result ->
            onResult(result)
            removeResult()
        }
    }

    companion object {
        inline fun <reified T> create(
            navController: NavController,
            key: String
        ): NavResultHandler<T> {
            return NavResultHandler(navController, key)
        }
    }
}

// Extension function for easier creation
inline fun <reified T> NavController.resultHandler(key: String): NavResultHandler<T> {
    return NavResultHandler.create(this, key)
}