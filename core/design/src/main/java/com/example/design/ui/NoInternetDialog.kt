package com.example.design.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun NoInternetDialog(
    showDialog: Boolean,
    onRetry: () -> Unit = {},
    onDismiss: (Boolean) -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss(false) },
            title = { Text(text = "No Internet Connection") },
            text = { Text(text = "It looks like your internet connection is unavailable. Please check your connection and try again.") },
            confirmButton = {
//                Button(onClick = {
//                    onRetry()
//                    showDialog.value = false
//                }) {
//                    Text("Retry")
//                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss(false) }) {
                    Text("Cancel")
                }
            }
        )
    }
}