package com.example.feature.search.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.design.ui.HeaderSmall

@Composable
fun SearchError(message: String) {
    Column {
        Text(text = "Result Not Found", color = MaterialTheme.colorScheme.error)
        HeaderSmall(
            text = "Error: $message",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}