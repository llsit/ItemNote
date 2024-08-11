package com.example.recipe.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HeaderLarge(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier.padding(8.dp),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        text = text
    )
}

@Composable
@Preview(showBackground = true)
fun HeaderLargePreview() {
    Box(
        modifier = Modifier
            .wrapContentSize()
    ) {
        HeaderLarge(text = "What recipe are you looking for?")
    }
}