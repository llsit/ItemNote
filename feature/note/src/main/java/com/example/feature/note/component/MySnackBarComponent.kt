package com.example.feature.note.component

import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MySnackBarComponent(message: String) {
    Snackbar(
        modifier = Modifier
    ) {
        Text(message)
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewSnackBar() {
    MySnackBarComponent("Hello")
}