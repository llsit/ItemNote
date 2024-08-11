package com.example.recipe.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HeaderSmall(text: String) {
    Text(
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.titleSmall,
        text = text
    )
}