package com.example.feature.search.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SearchEmpty() {
    Box {
        Text(
            text = "No results found",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}