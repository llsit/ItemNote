package com.example.itemnote.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AlertDialogDefault(
    onDismissRequest: (() -> Unit)? = null,
    onConfirmation: (() -> Unit)? = null,
    dialogTitle: String = "",
    dialogText: String = "",
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            if (onDismissRequest != null) {
                onDismissRequest()
            }
        },
        confirmButton = {
            if (onConfirmation != null) {
                TextButton(
                    onClick = {
                        onConfirmation()
                    }
                ) {
                    Text("Confirm")
                }
            }
        },
        dismissButton = {
            if (onDismissRequest != null) {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text("Dismiss")
                }
            }
        }
    )
}