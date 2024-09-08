package com.example.core.common.utils

import android.content.Context
import com.example.core.common.connection.NetworkUtils.getStableInternetStatusFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

suspend fun <T> Flow<T>.collectWithCheckInternet(
    context: Context,
    onNoInternet: () -> Unit,
    onCollect: suspend (T) -> Unit
) {
    context.getStableInternetStatusFlow().first { isStable ->
        if (isStable) {
            this.collect { value ->
                onCollect(value)
            }
            true // Stop collecting from getStableInternetStatusFlow
        } else {
            onNoInternet()
            false // Continue checking for internet
        }
    }
}