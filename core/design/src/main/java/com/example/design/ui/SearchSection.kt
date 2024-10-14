package com.example.design.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchSection(
    modifier: Modifier,
    isEnable: Boolean = true,
    onValueChange: (String) -> Unit
) {
    val searchQuery by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = { onValueChange(it) },
        modifier = modifier.height(56.dp),
        placeholder = { Text("Search for your query") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        shape = RoundedCornerShape(28.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.LightGray.copy(alpha = 0.1f),
            focusedContainerColor = Color.LightGray.copy(alpha = 0.1f),
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent
        ),
        singleLine = true,
        enabled = isEnable
    )
}

@Composable
@Preview(showBackground = true)
fun SearchSectionPreview() {
    SearchSection(modifier = Modifier, onValueChange = {})
}